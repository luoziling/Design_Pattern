package priv.wzb.javabase.abstractAndInterface;

import java.lang.reflect.Method;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-05-29 13:45
 * @description: 静态方法检测
 **/

public abstract class StaticMethodMatcher implements MethodMatcher {
	@Override
	public boolean isRuntime() {
		return false;
	}

	@Override
	public boolean methes(Method method, Class<?> targetClass, Object... args) {
//		return false;
		throw new UnsupportedOperationException();
	}
}
