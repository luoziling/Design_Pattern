package priv.wzb.multi_threaded_high_concurrent.concurrent;

/**
 * @author Satsuki
 * @time 2019/5/29 17:59
 * @description:使用内部类的单例模式
 * 既不用枷锁，也能实现懒加载
 * https://www.cnblogs.com/xudong-bupt/p/3433643.html
 */
public class Singleton {
    private Singleton(){
        System.out.println("single");
    }

    private static class Inner{
        private static Singleton s = new Singleton();
    }

    private static Singleton getSingle(){
        return Inner.s;
    }

    public static void main(String[] args) {

    }
}
