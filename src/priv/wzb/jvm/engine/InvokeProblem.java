package priv.wzb.jvm.engine;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-23 16:44
 * @description:
 **/

public class InvokeProblem {
	class GrandFather {
		void thinking() {
			System.out.println("i am grandfather");
		}
	}
	class Father extends GrandFather {
		void thinking() {
			System.out.println("i am father");
		}
	}
	class Son extends Father {
		void thinking() {
// 请读者在这里填入适当的代码（不能修改其他地方的代码）
// 实现调用祖父类的thinking()方法，打印"i am grandfather"
			// 重写 动态分派
//			Class<? extends Son> sonClass = this.getClass();
//			Class<?> fatherClass = sonClass.getSuperclass();
//			Class<?> grandFatherClass = fatherClass.getSuperclass();
//
//			try {
//				Object grandFather = grandFatherClass.newInstance();
//				Method grandFatherThinkingMethod = null;
//				grandFatherThinkingMethod = grandFatherClass.getDeclaredMethod("thinking");
//				grandFatherThinkingMethod.invoke(grandFather);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			try {
				MethodType methodType = MethodType.methodType(void.class);
//				MethodHandle thinking = null;
//				// 调用public look up 使其去除限制 但是出错
////				thinking = lookup().findSpecial(GrandFather.class, "thinking", methodType,getClass());
//				thinking = publicLookup().findSpecial(GrandFather.class, "thinking", methodType,getClass());
//				thinking.invoke(this);
				// 破坏自带保护机制
				MethodType mt = MethodType.methodType(void.class);
				Field lookupImpl = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
				lookupImpl.setAccessible(true);
				MethodHandle mh = ((MethodHandles.Lookup) lookupImpl.get(null)).findSpecial(GrandFather.class,"thinking", mt, GrandFather.class);
				mh.invoke(this);
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}


		}
	}

	@Test
	public void test(){
		new Son().thinking();
//		Son son = new Son();
//		son.thinking();
	}

}
