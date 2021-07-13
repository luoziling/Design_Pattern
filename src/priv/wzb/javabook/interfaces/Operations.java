package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:49
 **/
public interface Operations {
    void execute();
    static void runOps(Operations... ops){
        for (Operations op : ops) {
            op.execute();
        }
    }

    static void show(String msg){
        System.out.println(msg);
    }
}
