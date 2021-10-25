package priv.wzb.spring.ioc.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");
		UserRepository userRepository = (UserRepository) beanFactory.getBean("userRepository");
		System.out.println("userRepository = " + userRepository);
		System.out.println("userRepository.getBeanFactory() = " + userRepository.getBeanFactory());
		System.out.println("beanFactory == userRepository.getBeanFactory() = " + (beanFactory == userRepository.getBeanFactory()));
		// 若类型为ApplicationContext则注入ClassPathXmlApplicationContext 与上面的BeanFactory实例的类型一致
		System.out.println("userRepository.getObjectFactory().getObject() = " + userRepository.getObjectFactory().getObject());
		System.out.println("userRepository.getObjectFactory().getObject() == beanFactory = " + (userRepository.getObjectFactory().getObject() == beanFactory));


	}
}
