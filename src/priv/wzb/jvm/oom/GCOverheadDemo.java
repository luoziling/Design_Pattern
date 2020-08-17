package priv.wzb.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2020/7/6 14:42
 * @description:
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * 将直接内存大小调小
 */
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println("***************"+i);
            e.printStackTrace();
            throw e;
        }
    }
}
