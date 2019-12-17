package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Tituo
 * @create 2019-12-16 21:10
 * 第三种创建线程的方法
 *
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask,"A").start();
        System.out.println(futureTask.get());
    }
}
class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("***********come in call");
        return "1024";
    }
}
