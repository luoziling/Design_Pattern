package priv.wzb.multi_threaded_high_concurrent.read_write_lock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 资源类
class MyCache{
    // 这里模拟缓存所以需要加上volatile保证可见性
    private volatile Map<String,Object> map = new HashMap<>();
//    private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();


    public void put(String key,Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写：" +key);
            // 模拟延迟，暂停线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
//        System.out.println(Thread.currentThread().getName() + "\t 正在写：" +key);
//        // 模拟延迟，暂停线程
//        try {
//            TimeUnit.MILLISECONDS.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        map.put(key,value);
//        System.out.println(Thread.currentThread().getName() + "\t 写入完成");
    }


    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取：" +key);
            // 模拟延迟，暂停线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
//        System.out.println(Thread.currentThread().getName() + "\t 正在读取：" +key);
//        // 模拟延迟，暂停线程
//        try {
//            TimeUnit.MILLISECONDS.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Object result = map.get(key);
//        System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + result);
    }

    // 缓存三大操作，读、写、清除
    public void clearMap(){
        map.clear();
    }

}

/**
 * @author Satsuki
 * @time 2020/5/24 21:23
 * @description:
 * 读写锁
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该同时进行
 * 但是
 * 如果一个线程想进去写共享资源，就不应该再有其他线程可以对该资源进行读或写
 * 小总结：
 * 读-读能共存
 * 读-写不能共存
 * 写-写不能共存
 *
 * 写操作：原子+独占，整个过程必须是一个完整的过程无法被打断
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }


        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }

    }
}
