package priv.wzb.jvm.engine;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-18 18:00
 * @description:
 **/

public class DynamicDispatch {
	static abstract class Human {
		protected abstract void sayHello();
	}
	static class Man extends Human {
		@Override
		protected void sayHello() {
			System.out.println("man say hello");
		}
	}
	static class Woman extends Human {
		@Override
		protected void sayHello() {
			System.out.println("woman say hello");
		}
	}
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		// man say hello
		man.sayHello();
		// woman say hello
		woman.sayHello();
		// woman say hello
		man = new Woman();
		man.sayHello();
	}
}
