package priv.wzb.jvm.engine;

import org.junit.Test;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-16 10:01
 * @description:
 **/

public class LocalTableTest {
	@Test
	public void t1() throws InterruptedException {
		// [ParOldGen: 65544K->67230K(167936K)] 对象过大 直接进入老年代，并没有被回收
		// -verbose：gc -XX:+PrintGCDetails
		byte[] placeholder = new byte[64 * 1024 * 1024];
		System.gc();
		Thread.sleep(500);
	}

	@Test
	public void t2() throws InterruptedException {
		// [ParOldGen: 65544K->67232K(167936K)] 对象过大 直接进入老年代，并没有被回收
		// -verbose：gc -XX:+PrintGCDetails
		{
			byte[] placeholder = new byte[64 * 1024 * 1024];
		}
		System.gc();
		Thread.sleep(500);
	}

	@Test
	public void t3() throws InterruptedException {
		// [ParOldGen: 65544K->1696K(167936K)] 对象过大 直接进入老年代，并没有被回收
		// -verbose：gc -XX:+PrintGCDetails
		{
			byte[] placeholder = new byte[64 * 1024 * 1024];
		}
		// 复用局部变量槽，触发变量槽的写操作 更新数据 通知GC
		int a = 0;
		System.gc();
		Thread.sleep(500);
	}

	boolean b;
	@Test
	public void t4(){
		int a;
		// java: 可能尚未初始化变量a
//		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}
}
