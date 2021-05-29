package priv.wzb.javabase.abstractAndInterface;

import java.lang.reflect.Method;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-05-29 13:46
 * @description: StaticMethodMatcher实现类
 **/

public class ConcreteStaticMethodMatcher extends StaticMethodMatcher {
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return false;
	}
	public static void main(String[] args) {
		System.out.println("new ConcreteStaticMethodMatcher().isRuntime() = " + new ConcreteStaticMethodMatcher().isRuntime());
	}
}
