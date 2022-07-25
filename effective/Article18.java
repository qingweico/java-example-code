package effective;

import java.util.*;

/**
 * 复合优先于继承
 *
 * @author zqw
 * @date 2021/3/3
 */
public class Article18 {
}

// Broken! - Inappropriate use of inheritance!
class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactory) {
        super(initCap, loadFactory);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(List.of("Snap", "Crackle", "Pop"));
        // 6 It's wrong
        System.out.println(s.getAddCount());
    }
}




// Wrapper class - uses composition in place of inheritance
class InstrumentedHashSetUseComposition<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedHashSetUseComposition(Set<E> s) {
        super(s);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSetUseComposition<String> s = new InstrumentedHashSetUseComposition<>(new HashSet<>());
        s.addAll(List.of("Snap", "Crackle", "Pop"));
        // 3
        System.out.println(s.getAddCount());
    }
}

// Reusable forwarding class
class ForwardingSet<E> implements Set<E> {

    private final Set<E> s;

    public ForwardingSet(Set<E> s) {
        this.s = s;

    }

    @Override
    public int size() {
        return s.size();
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return s.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return s.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return s.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public void clear() {
        s.clear();
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return s.equals(obj);
    }

    @Override
    public String toString() {
        return s.toString();
    }
}