package priv.wzb.spring.ioc.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import priv.wzb.spring.ioc.dependency.repository.UserRepository;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-20 19:43
 * @description: 依赖查找demo
 **/

public class DependencyInjectionDemo {
	public static void main(String[] args) {
		// 配置xml 启动上下文
		// 来源3：自建依赖
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");
		// 来源1：自定义bean
		UserRepository userRepository = (UserRepository) beanFactory.getBean("userRepository");
		System.out.println("userRepository = " + userRepository);
		System.out.println("userRepository.getBeanFactory() = " + userRepository.getBeanFactory());
		whoIsIocContainer(beanFactory, userRepository);
		// 若类型为ApplicationContext则注入ClassPathXmlApplicationContext 与上面的BeanFactory实例的类型一致
		System.out.println("userRepository.getObjectFactory().getObject() = " + userRepository.getObjectFactory().getObject());
		System.out.println("beanFactory = " + beanFactory);
		System.out.println("userRepository.getObjectFactory().getObject() == beanFactory = " + (userRepository.getObjectFactory().getObject() == beanFactory));
		// 来源2：内部自建bean
		Environment environment = beanFactory.getBean(Environment.class);
		System.out.println("environment = " + environment);


	}

	private static void whoIsIocContainer(BeanFactory beanFactory, UserRepository userRepository) {
		// beanFactory == userRepository.getBeanFactory() = false
		// applicationContext is BeanFactory
		// userRepository.getObjectFactory().getObject() = org.springframework.context.support.ClassPathXmlApplicationContext
		// userRepository.getBeanFactory() = org.springframework.beans.factory.support.DefaultListableBeanFactory
		// 构建的ClassPathXmlApplicationContext是一个applicationContext而userRepository.getBeanFactory()获取了一个BeanFactory 代表了虽然有上下级关系
		// 实质上一个 是增强的applicationContext另一个是基础的BeanFactory
		System.out.println("beanFactory == userRepository.getBeanFactory() = " + (beanFactory == userRepository.getBeanFactory()));
	}
}
