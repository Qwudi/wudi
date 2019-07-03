package com.hwq.thread.volatilestudyt;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class DataClass {
    volatile int num = 0;

    public void add(){
        num++;
    }
}
/*
public class com.hwq.thread.volatilestudyt.DataClass {
  volatile int num;

  public com.hwq.thread.volatilestudyt.DataClass();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: iconst_0
       6: putfield      #2                  // Field num:I
       9: return
    主要看这里：num ++ 的字节码操作，进行了三个操作，读，+，写回，不是原子操作。
    一个线程读了 加了 另一个线程可能加完已经写回了，这时第一个线程写回就给第二个线程写入的值覆盖了
  public void add();
    Code:
       0: aload_0
       1: dup
       2: getfield      #2                  // Field num:I
       5: iconst_1
       6: iadd
       7: putfield      #2                  // Field num:I
      10: return
}

 */
