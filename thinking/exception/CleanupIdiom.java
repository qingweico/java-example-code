package thinking.exception;

import cn.qingweico.io.Print;

/**
 * @author zqw
 * @date 2021/1/18
 */
public class CleanupIdiom {
    public static void main(String[] args) {
        NeedCleanup nc1 = new NeedCleanup();
        try {
            Print.println("nc1");
        } finally {
            nc1.dispose();
        }
        NeedCleanup nc2 = new NeedCleanup();
        NeedCleanup nc3 = new NeedCleanup();
        try {
            Print.println("nc2");
        } finally {
            nc3.dispose();
            nc2.dispose();
        }
        try {
            NeedCleanup2 nc4 = new NeedCleanup2();
            try {
                NeedCleanup2 nc5 = new NeedCleanup2();
                try {
                    Print.println("nc2");
                } finally {
                    nc5.dispose();
                }
            } catch (ConstructorException e) {
                Print.println(e);
            } finally {
                nc4.dispose();
            }
        } catch (ConstructorException e) {
            Print.println(e);
        }
    }
}

class NeedCleanup {
    private static long counter = 1;
    private final long id = counter++;

    public void dispose() {
        Print.println("NeedCleanup " + id + " dispose");
    }
}

class ConstructorException extends Exception {
}

class NeedCleanup2 extends NeedCleanup {
    public NeedCleanup2() throws ConstructorException {
    }
}
