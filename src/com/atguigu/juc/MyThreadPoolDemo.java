package com.atguigu.juc;

import java.util.concurrent.*;

/**
 * @author Tituo
 * @create 2019-12-17 19:32
 * 线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //一池5线程
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一池1线程
        //ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        //一池N线程
        //ExecutorService threadPool2 = Executors.newCachedThreadPool();

        //实际应用
        //java.util.concurrent.RejectedExecutionException
        ExecutorService threadPool =new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                //阻塞队列
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                /*DiscardPolicy接口ThreadPoolExecutor中的静态方法，即拒绝策略
                * 默认，直接抛出RejectedExecutionException异常阻止系统正常运行
                * */
                //new ThreadPoolExecutor.AbortPolicy()

                /*main	 受理业务	客户号：9
                * “调用者运行”一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，
                * 从而降低新任务的流量*/
                //new ThreadPoolExecutor.CallerRunsPolicy()

                /*
                该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。
                如果允许任务丢失，这是最好的一种策略。
                 */
                //new ThreadPoolExecutor.DiscardPolicy()

                /*
                抛弃队列中等待最久的任务，然后把当前任务加人队列中
                尝试再次提交当前任务。
                 */
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        //一个银行已经new好了5个受理窗口，有5个工作人员
        try{
            for (int i = 1; i <= 10 ; i++) {
                final int tempI = i;
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 受理业务\t客户号："+tempI);
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }
    }
}
