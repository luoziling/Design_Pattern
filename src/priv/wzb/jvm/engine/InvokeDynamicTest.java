package priv.wzb.jvm.engine;

import java.lang.invoke.*;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-23 16:31
 * @description:
 **/

public class InvokeDynamicTest {
	public static void main(String[] args) throws Throwable {
		INDY_BootstrapMethod().invokeExact("icyfenix");
	}
	public static void testMethod(String s) {
		System.out.println("hello String:" + s);
	}
	public static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws Throwable {
		// 2.根据类+方法+方法签名 获取指定方法调用
		return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
	}
	private static MethodType MT_BootstrapMethod() {
		return MethodType
				.fromMethodDescriptorString(
						"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String; Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
	}
	private static MethodHandle MH_BootstrapMethod() throws Throwable {
		// 1.获取引导方法
		return lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootstrapMethod());
	}
	private static MethodHandle INDY_BootstrapMethod() throws Throwable {
		// 3.构建调用封装对象CallSite cs
		CallSite cs = (CallSite) MH_BootstrapMethod().invokeWithArguments(lookup(), "testMethod",
				MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
		return cs.dynamicInvoker();
	}
}
