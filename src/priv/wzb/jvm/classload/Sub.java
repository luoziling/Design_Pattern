package priv.wzb.jvm.classload;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-10 17:53
 * @description:
 **/

public class Sub extends Parent{
	public static int B = A;
	static {
		System.out.println("Sub.static initializer");
	}

	public static void main(String[] args) {
		// 2
		System.out.println("Sub.A = " + Sub.A);
		// 2
		System.out.println("Sub.B = " + Sub.B);
		Sub sub = new Sub();
	}
}
