# 第四章 Spring Bean基础

## 31 | 定义Bean：什么是BeanDefinition？

BeanDefinition是spring framework种定义bean的配置元信息接口，包含：

- Bean的类名
- 下面都是Bean中的某些注解
- Bean行为配置元素，作用域、自动绑定模式、生命周期回调等
  - Bean中的一些注解扫描 主要标注在方法上一些回调方法
- 其他Bean引用，合作者（Collaborators）或者依赖（Dependencies）
- 配置属性，比如Bean属性（Properties）

## 32 | BeanDefinition元信息：除了Bean名称和类名，还有哪些Bean元信息值得关注？

| 属性（Property）         | 说明                                |
| ------------------------ | ----------------------------------- |
| Class                    | 全类名，必须是具体类，不能抽象/接口 |
| Name                     | Bean的名称或者id                    |
| Scope                    | 作用域入singleton/prototype         |
| Constructor arguments    | 构造参数 依赖注入                   |
| Properties               | 属性设置 setter 依赖注入            |
| Autowiring mode          | 自动绑定模式 例如byName             |
| Lazy Initialization mode | 延迟初始化模式（延迟/非延迟）       |
| Initialization method    | 初始化回调                          |
| Destruction method       | 销毁回调方法名称                    |



- BeanDefinition
  - Root
    - 具有多层级BeanDefinition的统一视图，支持多个bean合并
  - Generic
    - 最常用 最简单的实现，支持自定义角色
  - Child
    - 专用于表示bean之间的层级关系，子类拥有父类的公共属性和方法同时提供方法重写，在bean中也是这个关系。从其父级继承设置的 bean 的 Bean 定义。 子 bean 定义对父 bean 定义具有固定的依赖性。
- 

## 33 | 命名Spring Bean：id和name属性命名Bean，哪个更好？

### Bean 的名称

- 每个 Bean 拥有一个或多个标识符（identifiers），这些标识符在 Bean 所在的容器必须是 唯一的（**并非整个应用**）。通常，一个 Bean 仅有一个标识符，如果需要额外的，可考虑使用别名（Alias）来 扩充。
- 在基于 XML 的配置元信息中，开发人员可用 id 或者 name 属性来规定 Bean 的 标识符。通 常 Bean 的 标识符由字母组成，允许出现特殊字符。如果要想引入 Bean 的别名的话，可在 name 属性使用半角逗号（“,”）或分号（“;”) 来间隔。
- Bean 的 id 或 name 属性并非必须制定，如果留空的话，容器会为 Bean 自动生成一个唯一 的名称。Bean 的命名尽管没有限制，不过官方建议采用驼峰的方式，更符合 Java 的命名约 定。

### 命名 Spring Bean

- Bean 名称生成器（BeanNameGenerator）

- 由 Spring Framework 2.0.3 引入，框架內建两种实现： DefaultBeanNameGenerator：默认通用 BeanNameGenerator 实现

- AnnotationBeanNameGenerator：基于注解扫描的 BeanNameGenerator 实现，起始于 Spring Framework 2.5，关联的官方文档：

  > With component scanning in the classpath, Spring generates bean names for unnamed components, following the rules described earlier: essentially, taking the simple class name and turning its initial character to lower-case. However, in the (unusual) special case when there is more than one character and both the first and second characters are upper case, the original casing gets preserved. These are the same rules as defined by java.beans.Introspector.decapitalize (which Spring uses here).

- 默认实现:bean名称`beanName + GENERATED_BEAN_NAME_SEPARATOR+counter`

  - `BeanName+#+编号`

- 两种方式

  - spring自动生成
  - 自定义

- 

