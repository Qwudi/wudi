package com.hwq.thread;

/**
 * @Auther: haowenqiang
 * @Description:synchorinzed修饰方法和代码块的区别.字节码
 */
public class sync {
    public synchronized void method1(){
    }

    public void method2(){
        synchronized (this){
        }
    }
    //javap -v
/*
public com.hwq.thread.sync();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/hwq/thread/sync;

  public synchronized void method1();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=0, locals=1, args_size=1
         0: return
      LineNumberTable:
        line 9: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       1     0  this   Lcom/hwq/thread/sync;

  public void method2();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=3, args_size=1
         0: aload_0
         1: dup
         2: astore_1
         3: monitorenter
         4: aload_1
         5: monitorexit
         6: goto          14
         9: astore_2
        10: aload_1
        11: monitorexit
        12: aload_2
        13: athrow
        14: return
      Exception table:
         from    to  target type
             4     6     9   any
             9    12     9   any
      LineNumberTable:
        line 12: 0
        line 13: 4
        line 14: 14
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      15     0  this   Lcom/hwq/thread/sync;
      StackMapTable: number_of_entries = 2
        frame_type = 255
    offset_delta = 9
    locals = [ class com/hwq/thread/sync, class java/lang/Object ]
    stack = [ class java/lang/Throwable ]
    frame_type = 250
    offset_delta = 4
}
 */

}
