package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tituo
 * @create 2019-12-16 23:49
 */
public class ThreadOrderAccess1 {
    public static void main(String[] args) {
        ShareResource1 shareResource = new ShareResource1();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 1; i <= 10 ; i++) {
                shareResource.print();
            }
        },"C").start();
    }
}

class ShareResource1{

    /**1:A  2:B  3:C*/
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c = lock.newCondition();


    public void print(){
        lock.lock();
        try {
            //1.判断
            while (flag !=1) {
                //A系统禁止
                c.await();
            }
            //2.干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
                if (flag==1 && i==5){
                    break;

                }
                if (++flag==2 && i== 10){
                    break;
                }
                if (++flag==3){
                    flag = 1;

                }
            }
            c.signalAll();
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
                c.await();
            }
            //2.干活
            for (int i = 1; i <= 10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知C
            flag = 3;
            c.signal();
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
                c.await();
            }
            //2.干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知C
            flag = 1;
            c.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}


