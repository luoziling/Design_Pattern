package priv.wzb.javabase.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-15 21:52
 * @description:
 * VM ARGS: -Xms6m -Xmx6m -XX:+HeapDumpOnOutOfMemoryError
 **/

public class HeapOOM {
	static class OOMObject{

	}

	public static void main(String[] args) {
		List<OOMObject> list  = new ArrayList<>();
		while (true){
			list.add(new OOMObject());
		}
	}
}
