package priv.wzb.jvm.oom.book;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-05 19:33
 * @description:
 * VM Args：-Xmx20M -XX:MaxDirectMemorySize=10M
 **/

public class DirectMemoryOOM {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws Exception {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while (true) {
			unsafe.allocateMemory(_1MB);
		}
	}

}
