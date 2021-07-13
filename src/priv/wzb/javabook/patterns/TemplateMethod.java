package priv.wzb.javabook.patterns;

import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 10:29
 * @description:
 **/
abstract class ApplicationFramework {
	ApplicationFramework() {
		templateMethod();
	}

	abstract void customizel();

	abstract void customize2();

	/**
	 * "private"同时意味着自动的指定“final”
	 * 无法被继承和修改，类内访问
	 */
	private void templateMethod() {
		IntStream.range(0, 5).forEach(
				e -> {
					customizel();
					customize2();
				}
		);
	}
}

/**
 * create a new "application"
 * 重写抽象方法，改变模板方法的具体调用
 * 运行时自动装配具体实现的方法
 * 动态绑定
 */
class MyApp extends ApplicationFramework{
	@Override
	void customizel() {
		System.out.print("Hello ");
	}

	@Override
	void customize2() {
		System.out.println("World!");
	}
}

public class TemplateMethod {
	public static void main(String[] args) {
		new MyApp();
	}
}
