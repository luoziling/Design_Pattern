package priv.wzb.javabook.patterns.shapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 14:49
 * @description:
 **/

public class ShapeFactory2 implements FactoryMethod {
	Map<String, Constructor> factories = new HashMap<>(4);
	static Constructor load(String id){
		System.out.println("loading " + id);
		try {
			return Class.forName("priv.wzb.javabook.patterns.shapes." + id)
					.getConstructor();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Shape create(String type) {
		try {
			// computeIfAbsent如果key没有关联value就传入一个Future接口的实现（传入一段函数获取value）
			return (Shape) factories.computeIfAbsent(type,ShapeFactory2::load)
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		FactoryTest.test(new ShapeFactory2());
	}
}
