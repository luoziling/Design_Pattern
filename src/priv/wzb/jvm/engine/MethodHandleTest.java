package priv.wzb.jvm.engine;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-23 13:32
 * @description:
 **/

public class MethodHandleTest {
	static class ClassA{
		public void println(String s){
			System.out.println("s = " + s);
		}
	}

	@Test
	public void methodHandlerTest() throws Throwable {
		// System.out 返回的PrintStream对象是静态常量 因此打印方法需要加锁
		// 无论obj最终是哪个实现类，下面这句都能正确调用到println方法。
		Object obj = System.currentTimeMillis() % 2 == 0? System.out:new ClassA();
//		Class<ClassA> classAClass = ClassA.class;
//		classAClass.getMethod("test",String.class).invoke(obj,"t");
		MethodHandle mh = getMH(obj);
		mh.invoke("yuzuki");

	}

	private static MethodHandle getMH(Object obj) throws NoSuchMethodException, IllegalAccessException {
		// 生成方法句柄，在特定对象中寻找方法句柄，构建方法签名，根据方法签名+方法名寻找指定方法
		// MethodType：代表“方法类型”，包含了方法的返回值（methodType()的第一个参数）和
		// 具体参数（methodType()第二个及以后的参数）。
		MethodType methodType = MethodType.methodType(void.class, String.class);
		// special 是构造/父类/私有
		// static 是静态方法
		// 其余都是虚方法，在运行时确认
		// lookup()方法来自于MethodHandles.lookup，这句的作用是在指定类中查找符合给定的方法
		// 名称、方法类型，并且符合调用权限的方法句柄。
		// 因为这里调用的是一个虚方法，按照Java语言的规则，方法第一个参数是隐式的，代表该方法的接
		// 收者，也即this指向的对象，这个参数以前是放在参数列表中进行传递，现在提供了bindTo()
		// 方法来完成这件事情。
		return MethodHandles.lookup().findVirtual(obj.getClass(),"println",methodType).bindTo(obj);
	}
}
