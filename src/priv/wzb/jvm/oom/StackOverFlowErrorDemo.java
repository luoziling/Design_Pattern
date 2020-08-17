package priv.wzb.jvm.oom;

/**
 * @author Satsuki
 * @time 2020/7/5 22:10
 * @description:
 */
public class StackOverFlowErrorDemo {
    public static void main(String[] args) {
        stackOverFlowError();

    }

    private static void stackOverFlowError() {
        // 不断添加方法导致栈溢出
        // Exception in thread "main" java.lang.StackOverflowError
        stackOverFlowError();
    }
}
