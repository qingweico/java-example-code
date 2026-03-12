package effective;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 避免过度同步
 * <a href="https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-11/Chapter-11-Item-79-Avoid-excessive-synchronization.md">Item 79: Avoid excessive synchronization(避免过度同步)</a>
 * 在持有锁的情况下调用一个可能被子类重写的方法或外部代码, 由于这种调用是开放的, 因此无法控制被调用代码的行为
 * 并发设计中的 open call: 在不持有锁的情况下调用外部方(alien method)
 * @author zqw
 * @date 2021/3/27
 */
class Article79 {
    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
        set.addObserver((s, e) -> System.out.println(e));
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}

class ObservableSet<E> extends ForwardingSet<E> {

    public ObservableSet(Set<E> s) {
        super(s);
    }

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    private void notifyElementAdded(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
    }
    /**
     * Alien method moved outside synchronized block - open calls.
     * 在同步区域之外调用的外来方法称为 open call
     */
    private void notifyElementAdded0(E element) {
        List<SetObserver<E>> snapshot;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (SetObserver<E> observer : snapshot) {
            observer.added(this, element);
        }
    }

    @Override
    public boolean add(E e) {
        boolean added = super.add(e);
        if (added) {
            notifyElementAdded0(e);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            // Calls notifyElementAdded.
            result |= add(element);
        }
        return result;
    }

    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
        set.addObserver((s, e) -> System.out.println(e));


        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    set.removeObserver(this);
                }
            }
        });

        // Observer that uses a background thread needlessly.
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(() -> set.removeObserver(this)).get();
                        // Multi-catch
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}

@FunctionalInterface
interface SetObserver<E> {
    /**
     * Invoked when an element is added to the observable set.
     */
    void added(ObservableSet<E> set, E element);

}
// Thread-safe observable set with CopyOnWriteArrayList
class SafeObservableSet<E> extends ForwardingSet<E> {
    private final List<SafeSetObserver<E>> observers = new CopyOnWriteArrayList<>();

    public SafeObservableSet(Set<E> s) {
        super(s);
    }

    public void addObserver(SafeSetObserver<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver(SafeSetObserver<E> observer) {
        return observers.remove(observer);
    }

    private void notifyElementAdded(E element) {
        for (SafeSetObserver<E> observer : observers) {
            observer.added(this, element);
        }
    }

    @Override
    public boolean add(E e) {
        boolean added = super.add(e);
        if (added) {
            notifyElementAdded(e);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            // Calls notifyElementAdded.
            result |= add(element);
        }
        return result;
    }
}

@FunctionalInterface
interface SafeSetObserver<E> {
    /**
     * Invoked when an element is added to the observable set.
     */
    void added(SafeObservableSet<E> set, E element);
}
// 为了避免死锁和数据损坏, 永远不要从同步区域内调用外来方法
// 更一般地说, 将你在同步区域内所做的工作量保持在最小
