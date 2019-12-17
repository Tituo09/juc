
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriterLockDemo.java
 * PACKAGE_NAME
 *
 * @Description: TODO
 * @author: Administrator
 * @date: 2019/12/16 19:02
 */
public class Test11 {
    public static void main(String[] args) throws InterruptedException {
        ReadWriterMenthod readWriterMenthod = new ReadWriterMenthod();
        for (int i = 0; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {

                readWriterMenthod.writer(Thread.currentThread().getName() + temp, temp + "");

            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(3);
        for (int i = 0; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                readWriterMenthod.read(Thread.currentThread().getName() + temp);
            }, String.valueOf(i)).start();
        }
    }
}


class ReadWriterMenthod {
    private volatile Map<String, String> map = new HashMap<>();
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void writer(String key, String value) {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始写入");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }

    }


    public void read(String key) {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始读取");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结束" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }


    }


} 