package priv.wzb.design_pattern.createdpattern.singletonpattern.InitializationDemandHolder;




/**
 * @author Satsuki
 * @time 2019/6/17 17:52
 * @description:
 */
class Singleton {
    private Singleton(){}

    private static class HolderClass{
        private final static Singleton instance = new Singleton();
    }

    public static Singleton getInstance(){
        return HolderClass.instance;
    }
}
