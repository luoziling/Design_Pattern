package priv.wzb.spring.ioc.dependency.container;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import priv.wzb.spring.ioc.dependency.domain.User;

import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-27 20:17
 * @description: ioc 容器
 **/

public class IocContainerDemo {
	public static void main(String[] args) {
		// 自定义BeanFactory
		// xml场景+不需要复杂特性可用这个，但是一般都需要更高级特性
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 加载资源文件（xml
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		String location = "classpath:META-INF/dependency-injection-context.xml";
		int beanCount = xmlBeanDefinitionReader.loadBeanDefinitions(location);
		System.out.println("beanCount = " + beanCount);
		lookupGroup(beanFactory);
	}
	private static void lookupGroup(BeanFactory beanFactory) {
		User user = beanFactory.getBean(User.class);
		System.out.println("user = " + user);
		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
		System.out.println("userMap = " + userMap);
	}
}
