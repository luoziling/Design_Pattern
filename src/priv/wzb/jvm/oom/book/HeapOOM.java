package priv.wzb.jvm.oom.book;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-05 18:05
 * @description:
 * Java堆内存溢出异常测试
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 **/

public class HeapOOM {
	/**
	 * 静态内部类用于演示OOM（堆
	 */
	static class OOMObject{

	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		// 不断创建对象导致堆内存溢出
		while (true){
			list.add(new OOMObject());
		}
	}
}
