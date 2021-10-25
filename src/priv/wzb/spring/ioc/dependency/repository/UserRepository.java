package priv.wzb.spring.ioc.dependency.repository;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import priv.wzb.spring.ioc.dependency.domain.User;

import java.util.Collection;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-22 13:52
 * @description:
 **/
@Data
public class UserRepository {
	private Collection<User> users;
	private BeanFactory beanFactory;
	private ObjectFactory<ApplicationContext> objectFactory;
}
