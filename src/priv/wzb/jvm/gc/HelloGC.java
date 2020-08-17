package priv.wzb.jvm.gc;

/**
 * @author Satsuki
 * @time 2020/6/30 16:26
 * @description:
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("HelloGC");
        Thread.sleep(Integer.MAX_VALUE);
//        long totalMemory = Runtime.getRuntime().totalMemory();
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        System.out.println("maxMemory = " + maxMemory+ "字节" + maxMemory/1024/1024);
//        System.out.println("totalMemory = " + totalMemory+"字节" + totalMemory/1024/1024);

        // -Xms10m -Xmx10m -XX:+PrintGCDetails
        // 配置堆空间大小为10M但这里超出堆空间创建对象
//        byte[] byteArray = new byte[50*1024*1024];


    }
}
