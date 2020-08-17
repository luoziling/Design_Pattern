package priv.wzb.javabook.fp.run;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 17:55
 **/

public class RunnableMethodReference {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous");
            }
        }).start();
        new Thread(()->{
            System.out.println("lambda");
        }).start();

        // 直接将某个方法的逻辑传入接口来实现某个接口方法
        // 因为函数签名（返回值+参数列表）一致所以可以传递逻辑代码
        new Thread(Go::go).start();

    }
}
