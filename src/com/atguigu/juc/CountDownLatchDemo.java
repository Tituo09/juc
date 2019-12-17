package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author Tituo
 * @create 2019-12-16 19:22
 * 六个人上自习，班长锁门（火箭发射倒计时）
 *      递减
 *      执行时一个线程等待其他线程全部执行完毕后再执行
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t"+"离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t"+"锁门");
    }
}
