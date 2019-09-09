package priv.wzb.design_pattern.createdpattern.singletonpattern.Eager;

/**
 * @author Satsuki
 * @time 2019/6/17 17:40
 * @description:
 */
public class EagerSingleton {
    //在类加载时就实例化
    private static final EagerSingleton instance = new EagerSingleton();
    private EagerSingleton(){

    }
    public static EagerSingleton getInstance(){
        return instance;
    }
}
