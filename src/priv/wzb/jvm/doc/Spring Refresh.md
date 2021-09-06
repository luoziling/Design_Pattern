# [Spring Refresh](https://blog.csdn.net/qq_15037231/article/details/105938673)

1. **prepareRefresh**
   - 准备上下文环境 `initPropertySources`初始化参数校验
2. **postProcessBeanFactory** **invokeBeanFactoryPostProcessors**
   - BeanFactoryPostProcessor 在实例化前修改BeanDefinition
   - invokeBeanFactoryPostProcessors=》`ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry` 扫描并注册所有被@Compontent等注解标注的生成BeanDefinition
3. **registerBeanPostProcessors**
   - `BeanPostProcessor`初始化bean过程中修改行为
4. **finishBeanFactoryInitialization**
   - 初始化单例Bean 包含bean的实例化、属性填充、初始化

## 实例化和初始化

![](https://img-blog.csdnimg.cn/20200823200933330.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE1MDM3MjMx,size_16,color_FFFFFF,t_70#pic_center)

- spring中以Bean为基准使用对象
- Bean分为两部分
  - 实例化
    - 实例化完成已经有对象
    - 实例化之后聚合标签`@Autowired` `@Resource` `@Value`聚合为`MergedBeanDefinition`
  - 初始化
    - 依赖注入
    - 执行用户自定义逻辑
- BeanFactory和FactoryBean
  - BeanFactory是生产Spring bean对象的工厂对象
  - 而FactoryBean是一个Bean实例，可置入IOC容器，从容器获取时则直接获取到这个工厂Bean生产的Bean对象
    - 可以实现自己的复杂Bean初始化逻辑，而不依赖Spring的Bean生产流程
  - 
- 在`doGetBean#getSingleton`借助三级缓冲处理循环依赖 三级分别对应
  - bean初始化完成后
  - 属性注入后
  - 对象实例化后
  - A与B循环依赖实例化A，属性注入，发现需要B，尝试获取B的Bean对象，未发现 初始化B ，B的初始化过程中发现依赖A，从三级缓存获取未完全初始化的A以解决循环引用
- protoType不支持循环引用
- DependOn
  - 在doGetBean过程中从BeanDefinition中取出所有需要先进行实例化的bean
  - 调用getBean逐个优先实例化
- `AbstractBeanFactory#doGetBean`调用`DefaultSingletonBeanRegistry#getSingleton`
  - 前置检查
    - `beforeSingletonCreation(beanName);`
  - 函数式编程创建bean
    - `singletonObject = singletonFactory.getObject();`
  - 后置处理 移除正在创建中
    - `afterSingletonCreation(beanName);`
  - 存入singletonObjects
    - `addSingleton(beanName, singletonObject);`
- 主要看getObject 也就是`createBean`
  - 根据class属性解析class
  - `applyBeanPostProcessorsBeforeInstantiation` 调用实例化之前的BeanPostProcessor=》`InstantiationAwareBeanPostProcessor`
    - ![](https://img-blog.csdnimg.cn/20200515002921607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzE1MDM3MjMx,size_16,color_FFFFFF,t_70)
  - 三个方法
    - 作用于实例化之前
    - 作用于实例化之后
    - 作用于实例化之后依赖注入
  - 常见实现`AbstractAutoProxyCreator`
    - `shouldSkip(beanClass, beanName)`第一次加载 加载完所有Advisor
  - 
- 实际上调用`doCreateBean`,spring中实际干活的都是do开头，主体逻辑
  - 实例化bean封装未beanWrapper
  - 执行MergedBeanDefinitionPostProcessor，合并依赖注入的注解
  - 依赖注入
  - 初始化
  - 注册Bean的销毁逻辑
  - 返回构建好的bean
- 实例化=》createBeanInstance
  - 根据Supplier创建
  - 根据自定义工厂方法创建
  - 选择合适的构造器创建
    - 带参实例化
      - 确定参数
      - 确定构造函数
      - 参数验证
      - 加入缓存
      - 策略实例化
        - SimpleInstantiationStrategy/CglibSubclassingInstantiationStrategy
    - 
  - 
- 调用MergedBeanDefinitionPostProcessor的MergedBeanDefinitionPostProcessor 合并注解
- 处理循环引用
  - 参数加入singletonFactories中
  - 循环引用从`singletonFactories`getBean之后从这个缓存移除并加入`earlySingletonObjects`中
- 属性注入
  - `MutablePropertyValues`提供属性读写+深拷贝实现
  - 用于自定义属性填充
  - xml的byName/Type填充
  - @Autowired/@Resource的属性填充
    - 分别借助`AutowiredAnnotationBeanPostProcessor和CommonAnnotationBeanPostProcessor`属性填充
    - 通过解析依赖属性+反射属性注入
  - 
- 属性注入选择
  - 多个对象满足则规则注入
    - @Primary
    - @Priority
    - 名称匹配
  - 
- bean初始化
  - 检查aware
  - **执行BeanPostProcessor的postProcessBeforeInitialization方法** 执行@PostConstruct
  - 执行InitializingBean#afterProperties Set
  - **执行BeanPostProcessor的postProcessBeforeInitialization方法**
- aware在初始化之前执行
- postProcessAfterInitialization 执行过程中比较主要的是生成代理对象`AnnotationAwareAspectJAutoProxyCreator`
  - 获取@Aspect注解的bean 增强器
  - 获取匹配增强方法，根据增强器优先级进行方法扩展
  - 根据不同条件通过jdk/cglib生成动态代理对象
    - 接口用jdk
      - 只能处理实现接口的类
    - 无接口用cglib
      - 不能处理private final可处理static
  - 
- 

## Spring对循环依赖的处理

- prototype不支持循环依赖
  - 因为prototype类型bean的生命周期不由spring容器管理，属于即产即用型，所以spring并不会持有bean的引用，更不会有bean的缓存，所以无法实现循环依赖（循环依赖的实现需要缓存bean）
  - 没有bean缓存
- 构造器注入不支持循环依赖
  - 在实例化阶段就要解决循环依赖
  - 在实例化阶段无法处理这个问题
- field支持循环依赖 两种清空
  - 普通切面生成支持循环依赖
  - @Async不支持
- 

> 【spring循环依赖】三个重要的Map(“三级缓存”)
>
> singletonObjects:缓存所有构建好的bean
> earlySingletonObjects:在bean构建过程中提前暴露出来的“半成品”bean
> singletonFactories:用于创建“半成品”bean的工厂

## 小结

### IOC容器初始化大概流程

- 前置处理工作
  - 扫描@Component等注解，生成BeanDefinition
- refresh IOC容器
- 遍历BeanNames,依次初始化
- 处理循环依赖，若从`singletonFactories`中能获取则放入earlySingletonObjects中
- 处理dependsOn
- 实例化bean对象，封装为beanWrapper
  - 在实例化前后分别执行 `InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation/postProcessAfterInstantiation`
  - 按照supplier 自定义工厂方法 选择合适的构造器 进行实例化
- 依赖注入
  - 合并属性注解变为MetgedBeanDefinition
  - 参数加入singletonFactories中
  - 通过PostProcessor初始化@Autowired/@Reources
- 初始化
  - 在初始化前后分别执行`BeanPostProcessor#postProcessBeforeInitialization/postProcessAfterInitialization`
  - 初始化之后根据需要可转为代理对象
  - 完成bean初始化的放入`singletonObjects`容器



### 循环引用处理流程

- 构造、prototype无法处理循环引用
- 存在A B两个class文件，并且通过注解循环引用
- 实例化A，实例化之后支持循环引用则放入缓存`singletonFactories`
- 在依赖注入时发现B，B未初始化，初始化 B
- 实例化B之后在依赖注入时发现依赖A
- 在getBean过程中首先尝试从缓存中获取A，在三级缓存的`singletonFactories`中成功获取，以解决循环依赖
