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

