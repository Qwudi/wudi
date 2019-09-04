package com.hwq.thread.volatilestudyt;

/**
 * @Auther: haowenqiang
 * @Description: volatile修饰的双重检测的单例模式
 */
public class VolatileSingleton {
    //私有静态实例
    private volatile static VolatileSingleton instance = null;
    //私有构造方法，禁止外部new对象
    private VolatileSingleton(){}
    //提供获取对象的唯一入口
    public static VolatileSingleton getInstance(){
        if(instance == null){  //1
            synchronized(VolatileSingleton.class) {
                if(instance == null){ //2
                    instance = new VolatileSingleton();//3
                }
            }
        }
        return instance;
    }
    /*
    代码中的3处 new 一个对象分为三步
    1.给对象分配内存空间
    2.为对象初始化值
    3.设置instance实例指向步骤一分配的内存地址，此时的 instance!=null
    因为步骤2和步骤三没有数据依赖，123可能发生指令重排，可能的执行顺序是132
    我们先假设没有volatile修饰instance，假如两个线程线程A和线程B
    A线程先进来，instance肯定是null啊，这时也加锁了，要开始new对象了，执行到代码3处，此时发生了指令重排，
    按上面new操作顺序的132来执行，执行到设置了instance的指针，此时instance就不为null了，但是还没初始化值啊
    正好此时B线程进来了，一看instance != null了 直接返回！！
    此时就出现错误了
    加入volatile的作用就是禁止指令重排，强制按照123来new对象就不会发生上面的问题了。
     */
}
