package priv.wzb.interview.meituan;

/**
 * @author Satsuki
 * @time 2019/9/22 22:48
 * @description:
 */
public class SingletonLazy {
    // volatile保证，当uniqueInstance变量被初始化成Singleton实例时，多个线程可以正常处理uniqueInstance变量
    //也就是说当uniqueInstance对象初始化完成后将对所有线程可见
    private volatile static SingletonLazy uniqueInstance;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance(){
        //检查实例，不存在就进入同步代码块
        if (uniqueInstance==null){
            //多线程同步对当前类进行锁定同一时间只有一个线程能进入同步代码块
            //synchronized锁定小部分代码块减小粒度提升效率
            synchronized (SingletonLazy.class){
                //双重验证，保证未被实例化，如果仍是null才创建实例
                if (uniqueInstance==null){
                    uniqueInstance = new SingletonLazy();
                }
            }
        }
        return uniqueInstance;
    }


}
