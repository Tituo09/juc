package com.atguigu.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：三个售票员  卖出  30张票
 */
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            for (int i = 1; i <= 30 ; i++) {
                executorService.execute(()-> {ticket.sale();});
            }
        }finally {
            executorService.shutdown();
        }
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 35; i++) {
                    ticket.sale();
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 35; i++) {
                    ticket.sale();
                }
            }
        }, "B").start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 35; i++) {
//                    ticket.sale();
//                }
//            }
//        }, "C").start();*/
//        new Thread(()->{for(int i = 0; i < 35; i++) ticket.sale();},"C");
        

    }
}


class Ticket {
    private int num = 30;
    private Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (num--) + "剩" + num + "张");
            }
        } finally {
            lock.unlock();
        }



    }
//    public synchronized void sale() {
//        if (num > 0) {
//            System.out.println(Thread.currentThread().getName() + "卖出第" + (num--) + "剩" + num + "张");
//        }
//    }
}
