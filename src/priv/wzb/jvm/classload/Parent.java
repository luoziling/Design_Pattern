package priv.wzb.jvm.classload;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-10 17:53
 * @description:
 **/

public class Parent {
	public static int A = 1;
	static {
		A = 2;
		System.out.println("Parent.static initializer");
	}
}
