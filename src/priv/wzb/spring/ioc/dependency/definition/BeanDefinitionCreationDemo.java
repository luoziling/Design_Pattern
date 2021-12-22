package priv.wzb.spring.ioc.dependency.definition;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import priv.wzb.spring.ioc.dependency.domain.User;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-12-06 14:46
 * @description: 创建beanDefinition
 **/

public class BeanDefinitionCreationDemo {
	public static void main(String[] args) {
		// 两种创建beanDefinition的方法
		// 1.通过BeanDefinitionBuilder构建
		// BeanDefinitionBuilder 私有化默认构造器，只能使用builder的模式去实例化
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		beanDefinitionBuilder.addPropertyValue("name", "yuzuki").addPropertyValue("id",1);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

		// 普通的BeanDefinition实现，若包含层级关系则推荐Root/Child 两种其他的BeanDefinition实现
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(User.class);
		MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
//		mutablePropertyValues.addPropertyValue("id",1);
//		mutablePropertyValues.addPropertyValue("name", "yuzuki");
		mutablePropertyValues.add("id",1).add("name", "yuzuki");

		genericBeanDefinition.setPropertyValues(mutablePropertyValues);
	}
}
