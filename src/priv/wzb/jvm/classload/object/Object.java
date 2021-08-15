package priv.wzb.jvm.classload.object;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-11 14:15
 * @description:
 **/

public class Object {
	static {
		System.out.println("Object.static initializer");
	}

	public static void main(String[] args) {
		Object object = new Object();
		System.out.println("object instanceof java.lang.Object = " + object instanceof java.lang.Object);
	}
}
