package priv.wzb.jvm.oom;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2020/7/6 15:07
 * @description:
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:" + (sun.misc.VM.maxDirectMemory()/(double)1024/1024) + "MB");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);
    }
}
