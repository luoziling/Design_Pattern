package priv.wzb.jvm.classload;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-10 17:38
 * @description:
 **/

public class AClass {
	static {
		System.out.println("AClass.static initializer");
	}

	{
		System.out.println("AClass.instance initializer");
	}

	public static void main(String[] args) {
		AClass c1 = new AClass();
		AClass c2 = new AClass();
	}
}
