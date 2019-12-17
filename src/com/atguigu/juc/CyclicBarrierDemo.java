package com.atguigu.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Tituo
 * @create 2019-12-16 19:35
 * 七龙珠，集齐之后召唤神龙
 *      递增
 *      当前线程等所有线程执行完毕后再执行该线程
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
       // CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("*******集齐七龙珠，召唤神龙");
        });
        for (int i = 1; i <= 7 ; i++) {
            int finalI = i;

            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"\t 收集到第："+ finalI +"\t龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

