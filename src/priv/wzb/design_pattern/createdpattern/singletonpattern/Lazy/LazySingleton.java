package priv.wzb.design_pattern.createdpattern.singletonpattern.Lazy;

/**
 * @author Satsuki
 * @time 2019/6/17 17:41
 * @description:
 */
public class LazySingleton {
    private static LazySingleton instance=null;
    private volatile static LazySingleton instance1=null;
    private LazySingleton(){

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
}
