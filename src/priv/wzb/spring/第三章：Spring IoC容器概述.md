# 第三章：Spring IoC容器概述

> ioc如何使用

## 22 | Spring IoC依赖查找：依赖注入还不够吗？依赖查找存在的价值几何？

- 根据 Bean 名称查找
  - 实时查找
  - 延迟查找
    - 不等于延迟加载bean
- 根据 Bean 类型查找
  - 单个 Bean 对象
  - 集合 Bean 对象
- 根据 Bean 名称 + 类型查找
- 根据 Java 注解查找
  - 单个 Bean 对象
  - 集合 Bean 对象

### ObjectFactory

- objectFactory和BeanFactory和FactoryBean的区别



**通过BeanFactory/ListableBeanFactory可根据id/名称/组/注解来获取bean实例**

- IOC依赖查找分类

```java
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
```

## 23 | Spring IoC依赖注入：Spring提供了哪些依赖注入模式和类型呢？

- 根据名称注入
- 类型注入
  - 单个/集合
  - 类型自动注入，包括集合也可通过bytype一次注入
- 注入容器内建Bean对象
- 注入非Bean对象
- 注入类型
  - 实时
  - 延迟
- 依赖查找和依赖注入的区别
  - 来源区别依赖注入和依赖查找的来源不同，依赖查找是BeanFactory spring内部使用
  - 依赖注入是applicationContext主要给外部使用

## 24 | Spring IoC依赖来源：依赖注入和查找的对象来自于哪里？

- 自定义 Bean
- 容器內建 Bean 对象
  - 容器内部bean
  - 容器内部构建的bean
  - Environment
- 容器內建依赖
  - spring对于其他的依赖，依赖注入
  - BeanFactory

## 25 | Spring IoC配置元信息：Spring IoC有哪些配置元信息？它们的进化过程是怎样的？

- Bean 定义配置 xml中配置的bean基础信息例如id name
  - 基于 XML 文件
  - 基于 Properties 文件
  - 基于 Java 注解
  - 基于 Java API（专题讨论）
- IoC 容器配置
  - 基于 XML 文件
  - 基于 Java 注解
  - 基于 Java API （专题讨论）
- 外部化属性配置
  - 基于 Java 注解 例如@Value+properties

## 26 | Spring IoC容器：BeanFactory和ApplicationContext谁才是Spring IoC容器？

BeanFactory 和 ApplicationContext 谁才是 Spring IoC 容器？

> The [`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.3.12/javadoc-api/org/springframework/beans/factory/BeanFactory.html) interface provides an advanced configuration mechanism capable of managing any type of object. [`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.12/javadoc-api/org/springframework/context/ApplicationContext.html) is a sub-interface of `BeanFactory`. It adds:
>
> - Easier integration with Spring’s AOP features
> - Message resource handling (for use in internationalization)
> - Event publication
> - Application-layer specific contexts such as the `WebApplicationContext` for use in web applications.
>
> In short, the `BeanFactory` provides the configuration framework and basic functionality, and the `ApplicationContext` adds more enterprise-specific functionality. The `ApplicationContext` is a complete superset of the `BeanFactory` and is used exclusively in this chapter in descriptions of Spring’s IoC container. For more information on using the `BeanFactory` instead of the 
>
> `ApplicationContext,` see [The `BeanFactory`](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanfactory).

- applicationContext是BeanFactory的超集，在BeanFactory基础上提供了大量额外的有用特性

- BeanFactory是一个基础的ioc容器提供了一些对象管理能力

- `AbstractApplicationContext`

  - ```java
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
    ```

  - 

- `ConfigurableApplicationContext`extends `ApplicationContext` extends `BeanFactory`

  - 里氏代换原则，所有用到父类对象的地方可以用子类代替，反之不行

- `AbstractRefreshableApplicationContext`中组合一个`DefaultListableBeanFactory`并且继承`AbstractApplicationContext`

- 类似代理模式，实现这个接口内部缺维护了一个用来增强的对象

### 小结

- BeanFactory和ApplicationContext是同一类事务

- 底层ApplicationContext组合了一个BeanFactory的实现以提供更强的功能

- ```java
  	private static void whoIsIocContainer(BeanFactory beanFactory, UserRepository userRepository) {
  		// beanFactory == userRepository.getBeanFactory() = false
  		// applicationContext is BeanFactory
  		// userRepository.getObjectFactory().getObject() = org.springframework.context.support.ClassPathXmlApplicationContext
  		// userRepository.getBeanFactory() = org.springframework.beans.factory.support.DefaultListableBeanFactory
  		// 构建的ClassPathXmlApplicationContext是一个applicationContext而userRepository.getBeanFactory()获取了一个BeanFactory 代表了虽然有上下级关系
  		// 实质上一个 是增强的applicationContext另一个是基础的BeanFactory
  		System.out.println("beanFactory == userRepository.getBeanFactory() = " + (beanFactory == userRepository.getBeanFactory()));
  	}
  ```

- 获取了applicationContext要调用getBeanFactory方法获取内部的BeanFactory

## 27 | Spring应用上下文：ApplicationContext除了IoC容器角色，还提供哪些特性？

ApplicationContext 除了 IoC 容器角色，还有提供：

- 面向切面（AOP）

- 配置元信息（Configuration Metadata）

  - Environment对象
  - 支撑@Bean

- 资源管理（Resources）

  - url
  - class loading

- 事件（Events）

- 国际化（i18n）

- 注解（Annotations）

  - @Autowired是在BeanFactory中支撑但是需要ApplicationContext来驱动
  - @Component/ComponentScan都是通过ApplicationContext来驱动

- Environment 抽象（Environment Abstraction）

  

## 28 | 使用Spring IoC容器：选BeanFactory还是ApplicationContext？

- BeanFactory 是 Spring 底层 IoC 容器
  - bean的定义、配置、管理
  - XmlBeanDefinitionReader
- ApplicationContext 是具备应用特性的 BeanFactory 超集

IOC容器的两种加载/启动方式

- 基于xml container的启动

  - ```java
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
    ```

  - 

- 基于注解的IOC容器

  - ```java
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
    ```

  - 

## 29 | Spring IoC容器生命周期：IoC容器启停过程中发生了什么？

- 启动

  - ```java
    // 防止多线程并发刷新容器导致问题
    synchronized (this.startupShutdownMonitor)
    // 准备初始化 主要是properties
    prepareRefresh();
    // 获取beanFactory 我的版本没有加锁代表refreshBeanFactory只是在容器初始化使用
    refreshBeanFactory();
    // 上下文初始化，初始化ioc所需的一些bean对象
    prepareBeanFactory(beanFactory);
    // beanFactory初始化完成后的自定义实现方案
    postProcessBeanFactory(beanFactory);
    // Invoke factory processors registered as beans in the context.
    // 调整容器 一些容器后置处理器的调用
    invokeBeanFactoryPostProcessors(beanFactory);
    
    // Register bean processors that intercept bean creation.
    // 调整bean bean后置处理器的注册
    registerBeanPostProcessors(beanFactory);
    // 国际化
    initMessageSource();
    ```

  - 基本操作

    - 创建BeanFactory
    - 初步初始化
    - 加入内建的bean/非bean依赖
    - 注册beanPostProcessor

  - 总的来说就是beanFactory自身的初始化以及容器的初始化

  - 两边都会有一些BeanPostProcessor进行前后扩展，并且在初始化过程中添加了 事件、国际化等特性

- 运行

- 停止

  - 销毁所有的bean
  - 关闭bean工厂

## 30 | 面试题精选

- 什么是spring ioc容器

  - ```java
    Spring Framework implementation of the Inversion of Control (IoC) principle. IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method.
    The container then injects those dependencies when it creates the bean.
    ```

  - 

  - spring来管理bean对象整体生命周期的容器，并通过该容器为bean增强了很多特殊功能

  - DI的实现 依赖注入/查找

  - DI是IOC的实现，通过各种方法实现依赖注入例如setter/构造，bean对象进入IOC容器

- BeanFactory 与 FactoryBean？

  - BeanFactory 是 IoC 底层容器
  - FactoryBean是 创建 Bean 的一种方式，帮助实现复杂的初始化逻辑
    - 非正常处理的bean
    - 例如第三方创建的bean 反序列化得到的？
    - 暴露的bean是一个工厂，就像通过工厂创建对象可以提供额外的其他操作，并非直接暴露bean自身
  - POJO对象
  - FactoryBean是否也会经历bean的生命周期呢

- Spring IoC 容器启动时做了哪些准备？

  - 容器自身的初始化和后置操作
  - 初始化各类bean对象，包括实例化、参数注入、初始化，后续可能做一些增强
  - IoC 配置元信息读取和解析、IoC 容器生命周期、Spring 事件发布、 国际化等，更多答案将在后续专题章节逐一讨论

- 

