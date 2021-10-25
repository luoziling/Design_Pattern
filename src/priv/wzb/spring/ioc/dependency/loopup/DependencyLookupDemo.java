package priv.wzb.spring.ioc.dependency.loopup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import priv.wzb.spring.ioc.dependency.annotation.Super;
import priv.wzb.spring.ioc.dependency.domain.User;

import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-20 19:43
 * @description: 依赖查找demo
 **/

public class DependencyLookupDemo {
	public static void main(String[] args) {
		// 配置xml 启动上下文
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-lookup-context.xml");
		// 实时查找
		lookupInRealTime(beanFactory);
		lookupLazy(beanFactory);
		lookupGroup(beanFactory);
		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		Map<String, Object> beansWithAnnotation = listableBeanFactory.getBeansWithAnnotation(Super.class);
		System.out.println("beansWithAnnotation = " + beansWithAnnotation);

	}

	private static void lookupGroup(BeanFactory beanFactory) {
		User user = beanFactory.getBean(User.class);
		System.out.println("user = " + user);
		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
		System.out.println("userMap = " + userMap);
	}

	private static void lookupLazy(BeanFactory beanFactory) {
		// 延时查找
		ObjectFactory objectFactory = (ObjectFactory) beanFactory.getBean("objectFactory");
		User user = (User) objectFactory.getObject();
		System.out.println("user = " + user);
	}

	private static void lookupInRealTime(BeanFactory beanFactory) {
		User user = (User) beanFactory.getBean("user");
		System.out.println("user = " + user);
	}
}
