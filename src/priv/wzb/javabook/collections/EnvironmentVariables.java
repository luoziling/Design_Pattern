package priv.wzb.javabook.collections;

import java.util.Map;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-01 16:26
 **/

public class EnvironmentVariables {
    public static void main(String[] args) {
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
