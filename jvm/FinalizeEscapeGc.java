package jvm;

import cn.qingweico.io.Print;

/**
 * finalize method
 * Objects can save themselves when they are GC.
 * @author zqw
 * @date 2021/3/31
 */
class FinalizeEscapeGc {
    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive() {
        Print.println("yes, i am still alive");
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Print.println("finalize method executed!");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        SAVE_HOOK = null;

        // Finalize methods can only be called
        // automatically by the system at most once.
        System.gc();


        Thread.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            Print.println("oh no, i am dead!");
        }

        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            Print.println("oh no, i am dead!");
        }
    }
}
