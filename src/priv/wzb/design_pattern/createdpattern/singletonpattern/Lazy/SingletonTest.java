package priv.wzb.design_pattern.createdpattern.singletonpattern.Lazy;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-27 20:45
 * @description:
 **/

public class SingletonTest {
	public static void main(String[] args) {
//		System.out.println("LazySingleton.getInstance() = " + LazySingleton.getInstance());
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				System.out.println("LazySingleton.getInstance2() = " + LazySingleton.getInstance2());
			}).start();
		}

	}
}
