package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tituo
 * @create 2019-12-15 21:16
 * 题目：现在两个线程，可以操作初始值为零的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1
 * 实现交替，来10轮，变量初始值为0
 *
 * 1 高内聚低耦合前提下，线程操作资源类
 * 2 判断、干活、通知
 * 3 防止多线程通信时，虚假唤醒的bug在wait用while
 *
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) throws Exception{
        AirConditioner airConditioner = new AirConditioner();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10 ; i++) {
                    try {
                        airConditioner.increment();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10 ; i++) {
                    try {
                        airConditioner.decrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "B").start();

    }
}

class AirConditioner{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            //判断
            while (number != 0){
                //this.wait();
                condition.await();
            }
            //干活
            ++number;
            System.out.println(Thread.currentThread().getName() + number);
            //通知
            //this.notifyAll();
            condition.signalAll();
          } catch (Exception e) {
              e.printStackTrace();
          }finally {
             lock.unlock();
          }

    }
    public synchronized void decrement() throws InterruptedException {
       while (number ==0 ){
           this.wait();
       }
        --number;
        System.out.println(Thread.currentThread().getName() +number);
        this.notifyAll();
    }
}
