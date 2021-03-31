package jvm;

import static util.Print.print;

/**
 * Objects can save themselves when they are GC.
 * @author:qiming
 * @date: 2021/3/31
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        print("yes, i am still alive");
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        print("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        SAVE_HOOK = null;
        // Finalize methods can only be called
        // automatically by the system at most once.
        System.gc();


        Thread.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            print("oh no, i am dead!");
        }

        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            print("oh no, i am dead!");
        }

    }

}
