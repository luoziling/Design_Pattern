package priv.wzb.jvm.oom;

/**
 * @author Satsuki
 * @time 2020/7/6 15:24
 * @description:
 * 不要轻易测试否则容易死机
 * 在VM环境中测试程序创建线程过多会无法退出只能切换至root强制杀死某个程序
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        // 调用两次start方法
        // 报错：Exception in thread "main" java.lang.IllegalThreadStateException
        // 原因是调用一次后Thread内部的参数改变已经开启一个线程再次调用报错
        // started = true;

//        Thread thread = new Thread();
//        thread.start();
//        thread.start();

        for (int i = 0; ; i++) {
            System.out.println("******************i=" + i);
            new Thread(()->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },""+i).start();

        }
    }
}
