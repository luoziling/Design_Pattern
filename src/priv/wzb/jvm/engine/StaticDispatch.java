package priv.wzb.jvm.engine;

import java.util.Random;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-18 13:48
 * @description: 静态分派
 **/

public class StaticDispatch {
	static abstract class Human {
	}

	static class Man extends Human {
	}

	static class Woman extends Human {
	}

	public void sayHello(Human guy) {
		System.out.println("hello,guy!");
	}

	public void sayHello(Man guy) {
		System.out.println("hello,gentleman!");
	}

	public void sayHello(Woman guy) {
		System.out.println("hello,lady!");
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		StaticDispatch sr = new StaticDispatch();
		// hello,guy!
		sr.sayHello(man);
		// hello,guy!
		sr.sayHello(woman);

		// 实际类型变化
		Human human = (new Random()).nextBoolean() ? new Man() : new Woman();
// 静态类型变化
		// hello,gentleman!
		//Exception in thread "main" java.lang.ClassCastException: priv.wzb.jvm.engine.StaticDispatch$Man cannot be cast to priv.wzb.jvm.engine.StaticDispatch$Woman
		//	at StaticDispatch.main(StaticDispatch.java:47)
		sr.sayHello((Man) human);
		sr.sayHello((Woman) human);

	}
}
