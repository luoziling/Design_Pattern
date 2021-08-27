package priv.wzb.jvm.classload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-26 14:30
 * @description:
 **/

public class DynamicProxyTest {
	interface IHello {
		void sayHello();
	}
	static class Hello implements IHello {
		@Override
		public void sayHello() {
			System.out.println("hello world");
		}
	}
	static class DynamicProxy implements InvocationHandler{
		Object originObject;

		Object bind(Object originObject) {
			this.originObject = originObject;
			return Proxy.newProxyInstance(originObject.getClass().getClassLoader(), originObject.getClass().getInterfaces(), this);
		}


		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("welcome");
			return method.invoke(originObject,args);
		}
	}

	public static void main(String[] args) {
		IHello hello = (IHello) new DynamicProxy().bind(new Hello());
		hello.sayHello();
	}
}
