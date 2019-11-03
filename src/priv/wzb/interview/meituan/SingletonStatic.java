package priv.wzb.interview.meituan;

/**
 * @author Satsuki
 * @time 2019/9/22 22:54
 * @description:
 * 使用静态内部类线程安全且为懒汉
 */
public class SingletonStatic {
    private SingletonStatic() {
    }

    //由于是静态内部类所以在调用该类时就会实例化final表示不可改变
    public static class StaticInner{
        private static final SingletonStatic INSTANCE = new SingletonStatic();
    }

    public static final  SingletonStatic getInstance(){
        return StaticInner.INSTANCE;
    }



}
