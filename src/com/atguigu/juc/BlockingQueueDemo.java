package com.atguigu.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Tituo
 * @create 2019-12-17 18:06
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a", 1L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 1L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 1L, TimeUnit.SECONDS));
        //线程执行超过一定时间会输出false并退出
        //System.out.println(blockingQueue.offer("d", 1L, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        //线程执行
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));   //null


        /*阻塞的插入、移除方法    put、take
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //当执行put方法时当向阻塞队列中添加的元素超过当前阻塞队列的值时，
        // 队列会一直阻塞，直到put数据或者相应中段
        //blockingQueue.put("x");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        //当执行take移除方法时，若移除的元素超过阻塞队列中的元素数时，会出现队列阻塞直到队列可用
        //blockingQueue.take();*/


        /*特殊值的插入、移除、检查方法 offer、poll、peek
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //使用offer（）方法，在超出阻塞队列时会输出false
        System.out.println(blockingQueue.offer("d"));   //false
        //取阻塞队列中的第一列
        //System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //使用poll（）方法进行移除时，当阻塞队列为空时继续执行移除操作会输出null
        System.out.println(blockingQueue.poll());       //null*/


        /*
        抛出异常的插入、移除、检查方法  add、remove、element
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //当阻塞队列已满时再次添加出现
        // Exception in thread "main" java.lang.IllegalStateException: Queue full
        //System.out.println(blockingQueue.add("d"));

        //element()检查方法只取队列中的第一个
        //System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //当阻塞队列为空时再次执行移除操作出现异常：java.util.NoSuchElementException
        System.out.println(blockingQueue.remove());*/
    }
}
