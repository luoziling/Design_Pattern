package priv.wzb.spring.ioc.java.beans;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-19 14:35
 * @description:
 * {@link BeanInfoDemo}
 **/

public class BeanInfoDemo {
	@Test
	public void testArrayTest(){
//		Object[] objects = new Object[10];
//		ClassLayout classLayout = ClassLayout.parseInstance(new Person[1]);
//		ClassLayout classLayout = ClassLayout.parseInstance(new Person());
//		System.out.println(classLayout.toPrintable());
	}
	public static void main(String[] args) throws IntrospectionException {
		// 防止Object 的class带来的影响
		BeanInfo beanInfo = Introspector.getBeanInfo(Person.class,Object.class);
		Stream.of(beanInfo.getPropertyDescriptors())
				.forEach(propertyDescriptor-> {
					// propertyDescriptor允许添加属性编辑器 propertyEditor
					// GUI-> text(string) -> propertyType
					// name -> string
					// age -> Integer
					System.out.println("propertyDescriptor = " + propertyDescriptor);
					String name = propertyDescriptor.getName();
					if ("age".equals(name)){
						// 为“age”字段/属性 增加propertyEditor String->Integer
						propertyDescriptor.setPropertyEditorClass(StringToIntegerEditor.class);
//						propertyDescriptor.createPropertyEditor()
					}
				}
		);

	}

	/**
	 * 自定义PropertyEditor,原先接口难用，尝试用PropertyEditorSupport 直接支持重写了大部分接口方法
	 */
	class StringToIntegerEditor extends PropertyEditorSupport{
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// spring 3.0之前大量基于PropertiesEditor 类型元信息转换 或者其他数据操作
			Integer value = Integer.valueOf(text);
			// 转换后的set 方便后续get
			setValue(value);
		}
	}
}
