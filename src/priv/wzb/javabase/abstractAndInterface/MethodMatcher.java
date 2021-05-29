package priv.wzb.javabase.abstractAndInterface;

import java.lang.reflect.Method;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2021-05-29 13:38
 * @description: AOP_方法匹配接口
 **/
public interface MethodMatcher {
	/**
	 * method是否匹配，在AOP中就是method是否成为一个连接点/切点
	 * @param method
	 * @param targetClass
	 * @return
	 */
	boolean matches(Method method,Class<?> targetClass);

	/**
	 * 匹配后是否需动态调用3-arg匹配
	 * @return
	 */
	boolean isRuntime();

	/**
	 * matchers=true isRuntime=true时触发
	 * @param method
	 * @param targetClass
	 * @param args
	 * @return
	 */
	boolean methes(Method method,Class<?> targetClass,Object... args);
}
