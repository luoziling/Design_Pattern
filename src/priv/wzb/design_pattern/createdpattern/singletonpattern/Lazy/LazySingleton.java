package priv.wzb.design_pattern.createdpattern.singletonpattern.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/17 17:41
 * @description:
 */
public class LazySingleton {
    private static LazySingleton instance=null;
    /**
     * volatile用于保证可见性和阻止指令重排否则在初始化时可能因为重排获取null
     */
    private volatile static LazySingleton instance1=null;
    private LazySingleton(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("constructor");
    }
    synchronized public static LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
    public static LazySingleton getInstance1(){
        if(instance1 == null){
            synchronized (LazySingleton.class){
                if(instance1==null){
                    instance1 = new LazySingleton();
                }
            }
        }
        return instance1;
    }

    public static LazySingleton getInstance2(){
        if(instance == null){
            synchronized (LazySingleton.class){
                if(instance==null){
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
