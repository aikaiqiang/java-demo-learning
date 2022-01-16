package com.kaywall.gc.bean;

/**
 * @author aikaiqiang
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("is still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGc();

        // First time
        SAVE_HOOK = null;
        System.gc();
        // finalize 优先级低，暂停0.5s,等待执行；
        Thread.sleep(500);

        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("i am dead");
        }

        // Second time
        SAVE_HOOK = null;
        System.gc();
        // finalize 优先级低，暂停0.5s,等待执行；
        Thread.sleep(500);

        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("i am dead");
        }

    }
}
