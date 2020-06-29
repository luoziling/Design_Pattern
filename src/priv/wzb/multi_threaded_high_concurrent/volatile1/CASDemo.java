package priv.wzb.multi_threaded_high_concurrent.volatile1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Satsuki
 * @time 2020/5/19 13:10
 * @description:
 * CAS是什么?compareAndSwap
 * 比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        // main do thing....



        // 设置一个期望值一个更新值
        // 如果主内存中的值与期望值相等则更新并返回true
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data:" + atomicInteger.get());


        // 否则就不跟新并返回false
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data:" + atomicInteger.get());
    }
}
