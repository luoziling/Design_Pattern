package priv.wzb.multi_threaded_high_concurrent.container_safe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Satsuki
 * @time 2020/5/21 11:38
 * @description: 集合类不安全问题
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        // 并发出错演示
//        List<String> list = new ArrayList<>();


        // 解决方案1：Vector,这个类是加了synchronized的，线程安全
        // 但是耗时严重 public synchronized boolean add(E e) {
        // 直接在方法体上加了
//        List<String> list = new Vector<>();


        // 解决方案2：Collections.synchronizedList(new (线程不安全的容器类))
        // public void add(int index, E element) {
        //            synchronized (mutex) {list.add(index, element);}
        //        }
        // 可见，传入一共线程不安全的list，在Collections内部会自动创建一共线程安全的list
        // 这个相对于vector锁的粒度更小并没有锁定方法体，而是锁了代码块
        // 使用synchronzed
        // final Object mutex;     // Object on which to synchronize
        // mutex是线程锁定对象
//        List<String> list = Collections.synchronizedList(new ArrayList<>());


        // 解决方案3 ： CopyOnWriteArrayList
        // /** The lock protecting all mutators */
        //    final transient ReentrantLock lock = new ReentrantLock();
        //
        //    /** The array, accessed only via getArray/setArray. */
        //    private transient volatile Object[] array;
        // 使用reentrantlock可重入锁
        // 数组采用volatile修饰，组织指令重排，保证可见性，操作原子性由lock保证
        // /**
        //     * Appends the specified element to the end of this list.
        //     *
        //     * @param e element to be appended to this list
        //     * @return {@code true} (as specified by {@link Collection#add})
        //     */
        //    public boolean add(E e) {
        //        final ReentrantLock lock = this.lock;
        //        lock.lock();
        //        try {
        //            Object[] elements = getArray();
        //            int len = elements.length;
        //            Object[] newElements = Arrays.copyOf(elements, len + 1);
        //            newElements[len] = e;
        //            setArray(newElements);
        //            return true;
        //        } finally {
        //            lock.unlock();
        //        }
        //    }
        // 可见其add方法的过程是
        // 加锁
        // 复制产生新的数组
        // 将要加入的值放入新数组
        // 将数组指向新数组
        // 解锁
        List<String> list = new CopyOnWriteArrayList<>();



        // 多个线程写
//        for (int i = 0; i < 100; i++) {
//            new Thread(()->{
//                list.add(UUID.randomUUID().toString().substring(1,8));
//                System.out.println(list);
//            },String.valueOf(i)).start();
//        }

        // 报错 java.util.ConcurrentModificationException
        // 原因：多线程争抢修改
        // 解决方案
        // Vector/Collections.synchronized/CopyOnWrite


        // 测试set和map
        // 出错：java.util.ConcurrentModificationException
        // 解决方案与list相似
//        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();


//        for (int i = 0; i < 100; i++) {
//            new Thread(()->{
//                // subString[)
//                set.add(UUID.randomUUID().toString().substring(2,8));
//                System.out.println(set);
//            },String.valueOf(i)).start();
//        }


        // 测试map

        // 出错:java.util.ConcurrentModificationException
//        Map<String,String> map = new HashMap<>();
//        Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String,String> map = new ConcurrentHashMap<>();

        // 在ConcurrentHashMap中将可能出错的关键代码加入了synchronized进行代码块锁定
        //synchronized (f) {
        //                    if (tabAt(tab, i) == f) {
        //                        if (fh >= 0) {
        //                            binCount = 1;
        //                            for (Node<K,V> e = f;; ++binCount) {
        //                                K ek;
        //                                if (e.hash == hash &&
        //                                    ((ek = e.key) == key ||
        //                                     (ek != null && key.equals(ek)))) {
        //                                    oldVal = e.val;
        //                                    if (!onlyIfAbsent)
        //                                        e.val = value;
        //                                    break;
        //                                }
        //                                Node<K,V> pred = e;
        //                                if ((e = e.next) == null) {
        //                                    pred.next = new Node<K,V>(hash, key,
        //                                                              value, null);
        //                                    break;
        //                                }
        //                            }
        //                        }
        //                        else if (f instanceof TreeBin) {
        //                            Node<K,V> p;
        //                            binCount = 2;
        //                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
        //                                                           value)) != null) {
        //                                oldVal = p.val;
        //                                if (!onlyIfAbsent)
        //                                    p.val = value;
        //                            }
        //                        }
        //                    }
        //                }

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                map.put(UUID.randomUUID().toString().substring(2,8),UUID.randomUUID().toString().substring(2,8));
                System.out.println(map);
            },String.valueOf(i)).start();



        }


    }
}
