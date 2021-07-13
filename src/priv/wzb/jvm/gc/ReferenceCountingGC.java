package priv.wzb.jvm.gc;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-10 19:37
 * @description:
 * 循环引用对引用计数机制的破坏
 **/

public class ReferenceCountingGC {
	public Object instance = null;

	private static final int _1MB = 1024 * 1024;

	/**
	 * 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清除是否有回收过
	 */
	private byte[] bigSize = new byte[2 * _1MB];

	public static void testGC(){
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		// 循环引用
		objA.instance = objB;
		objB.instance = objA;

		// 清空对象,外部没有对该对象的引用但是内部却产生了循环引用
		objA = null;
		objB = null;

		// 假设发生GC对象是否被回收？
		System.gc();
	}
}
