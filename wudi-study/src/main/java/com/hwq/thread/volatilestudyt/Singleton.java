package com.hwq.thread.volatilestudyt;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class Singleton{
    private static volatile Singleton instance;

    private Singleton(){ }

    public Singleton getInstance() {
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
