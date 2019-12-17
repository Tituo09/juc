package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tituo
 * @create 2019-12-16 18:14
 *
 * 线程定制化调用通信
 *
 * 多线程之间按顺序调用，实现A先干->b—>C
 *
 * 三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......来10轮
 *
 * 1    高聚低合前提下，线程操作资源类
 * 2    判断/干活/通知
 * 3    多线程交互中，必须要防止多线程的虚假唤醒，也即（判断只用while，不能用if）
 * 4    一定要注意，标志位的修改更新
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print5();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print10();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print15();
            }
        },"C").start();
    }
}

class ShareResource{

    /**1:A  2:B  3:C*/
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
                //1.判断
               while (flag !=1) {
                   //A系统禁止
                   c1.await();
               }
               //2.干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知B
            flag = 2;
            c2.signal();
          } catch (Exception e) {
              e.printStackTrace();
          }finally {
             lock.unlock();
          }
    }
    public void print10(){
        lock.lock();
        try {
                //1.判断
            while (flag != 2){
                c2.await();
            }
            //2.干活
            for (int i = 1; i <= 10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
                //3.通知C
            flag = 3;
            c3.signal();
          } catch (Exception e) {
              e.printStackTrace();
          }finally {
             lock.unlock();
          }
    }
    public void print15(){
        lock.lock();
        try {
            //1.判断
            while (flag != 3){
                c3.await();
            }
            //2.干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知C
            flag = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
