package priv.wzb.jvm.gc;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-07-06 18:28
 * @description: 年龄判断
 **/

public class AgeJudge {
	public static final int _1MB = 1024 * 1024;

	/**
	 * VM 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
	 * -XX:MaxTenuringThreshold=15
	 * -XX:PrintTenuringDistribution
	 * SuppressWarnings压制警告
	 * Override 重写 标注方法被重写，源码级别警告
	 * Deprecated 方法废弃 编译器警告使用被废弃的方法或继承实现该方法
	 * 观察运行结果可知 大对象直接分配到老年代
	 * 两个小对象在survivor区超出容量一半后直接进入老年代
	 */
	@SuppressWarnings("unused")
	public static void testTenuringThreshold2(){
		byte[] allocation1,allocation2,allocation3,allocation4;
		// 一个byte 8 bit 1个字节，new 生成数据每个元素 1 byte
		allocation1 = new byte[_1MB/4];
		allocation2 = new byte[_1MB/4];
		allocation3 = new byte[4*_1MB];
		allocation4 = new byte[4*_1MB];
		allocation4 = null;
		allocation4 = new byte[4*_1MB];
	}

	public static void main(String[] args) {
		testTenuringThreshold2();
	}
}
