package priv.wzb.spring.ioc.dependency.container;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import priv.wzb.spring.ioc.dependency.domain.User;

import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-27 20:17
 * @description: 基于注解的ioc 容器
 **/

public class AnnotationIocContainerDemo {
	public static void main(String[] args) {
		// 自定义BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 基于注解的context获取
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(AnnotationIocContainerDemo.class);
		// 启用上下文
		applicationContext.refresh();
		System.out.println("applicationContext.getBeanDefinitionCount() = " + applicationContext.getBeanDefinitionCount());
		lookupGroup(applicationContext);
		applicationContext.close();
	}
	@Bean
	public User getUser(){
		User user = new User();
		user.setId(1L);
		user.setName("aa");
		return user;
	}
	private static void lookupGroup(BeanFactory beanFactory) {
		User user = beanFactory.getBean(User.class);
		System.out.println("user = " + user);
		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
		System.out.println("userMap = " + userMap);
	}
}
