package priv.wzb.jvm.classload;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-10 17:45
 * @description:
 * 静态代码块与静态变量
 **/

public class StaticInit {
	static {
		i = 0;
		// Illegal forward reference
//		System.out.println("i = " + i);
	}
	{
		// i=1
		System.out.println("instance i = " + i);
	}
	static int i = 1;
	static {
		// i=1
		System.out.println("i = " + i);
	}

	public static void main(String[] args) {
		StaticInit staticInit = new StaticInit();
	}

}
