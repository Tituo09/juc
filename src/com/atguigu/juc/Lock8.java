package com.atguigu.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author Tituo
 * @create 2019-12-15 22:52
 *
 *  synchronized锁的是当前类
 *  static  不同的实例对象均来自同一个模板，Phone Class     加上static后锁的是模板
 *
 *  多线程8锁
 *  1   标准访问，先打印邮件还是短信  邮件
 *  2   email方法新增暂停4秒钟，先打印邮件还是短信    邮件
 *  3   新增普通的hello方法，先打印邮件还是hello   hello
 *  4   两部手机，先打印邮件还是短信  短信
 *  5   两个静态同步方法。1部手机，先打印邮件还是短信    邮件
 *  6   两个静态同步方法，2部手机，先打印邮件还是短信    邮件
 *  7   一个普通同步方法，一个静态同步方法，一部手机，先打印邮件还是短信    短信
 *  8   一个普通同步方法，一个静态同步方法，两部手机，先打印邮件还是短信    短信
 *
 * 解释
 * 1-2
 *      一个对象里面如果有多个synchronized方法，某个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *      其他的线程都只能等待，换句话说，某个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *      锁的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其他的synchronized方法
 *3-4
 *      加个普通方法后发现和同步锁无关
 *      换成两个对象后，不是同一把锁了，情况立刻变化
 *5-6
 *      都换成静态同步方法后，情况有变化
 *      若是普通同步方法，new    this，具体的同一部手机，所有的普通同步方法用的都是同一把锁——实例对象本身，
 *      若是静态同步方法，static Class，唯一的一个模板
 *      synchronized是实现同步的基础：Java中的每一个对象都可以作为锁
 *      具体表现为以下3种形式
 *      对于普通同步方法，锁是当前实例对象。他等同于  对于同步代码块，锁是synchronized括号里的配置的对象
 *      对于静态同步方法，锁是当前类的Class对象本身
 *7-8
 *      当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁
 *
 *      所有的普通同步方法用的都是同一把锁——实例对象本身，就是new出来的具体实例对象本身
 *      也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法不学等待获取锁的方法释放锁后才能获取锁
 *      可是别的实例对象的普通同步方法因为跟该实例对象的普通同步方法用的是不同的锁，所以不用等待该实例对象已经获取锁的普通
 *      同步方法释放锁，就可以获取他们自己的锁
 *
 *      所有的静态同步方法用的也是同一把锁——类的对象本身，也就是唯一的模板Class
 *      具体实例对象this和唯一模板Class吗，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有静态条件的
 *      但是一旦一个静态同步方法获取锁后，其他的静态同步方法都是必须等待该方法释放锁后才能获取锁
 *
 */
public class Lock8 {
    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(()->{
            try {
                Phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            try {
                //Phone.sendSMS();
                //phone.hello();
                Phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();
    }
}


class Phone{
    public static synchronized void sendEmail() throws Exception{
        //Thread.sleep(4000);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("*******sendEmail");
    }
    public static synchronized void sendSMS() throws Exception{
        System.out.println("-----------sendSMS");
    }
    public void hello(){
        System.out.println("=======hello");
    }
}
