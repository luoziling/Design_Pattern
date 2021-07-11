package priv.wzb.multi_threaded_high_concurrent.threadlocal;

/**
 * ThreadLocalTest
 *
 * @author yuzuki
 * @date 2021/7/11 16:42
 * @description:
 * @since 1.0.0
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        // 测试ThreadLocal中设置的某个遍历是否因ThreadLocalMap中entry的weakReference而被回收
        // 由于ThreadLocalMap的key就是ThreadLocal,而ThreadLocal没有重写equals以及hashCode两个方法所以借用Object对象的相等判断
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello");
        Thread thread = Thread.currentThread();
        // 提前GC
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s = threadLocal.get();
        System.out.println("s = " + s);
        threadLocal.remove();
        System.out.println("threadLocal.get() = " + threadLocal.get());
    }
}
