package priv.wzb.javabase.net.chat.chat04;

import java.io.Closeable;

/**
 * @author Satsuki
 * @time 2019/9/9 20:32
 * @description:
 */
public class WzbUtils {
    /**
     * 释放资源
     */
    public static void clese(Closeable... targets){
        for(Closeable target:targets){
            try {
                if(target!=null){
                    target.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
