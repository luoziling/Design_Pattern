# Design Pattern

## Factory Pattern

## 设计模式 工厂模式

工厂模式主要用于控制类生产的过程，避免客户类直接去使用具体类通过中间件工厂来降低耦合性。



### 简单工厂模式（Simple Factory Pattern)

UML图解（类图与时序图）

![1563869037427](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563869037427.png)

类图

![1563869554830](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563869554830.png)

时序图

java实现代码（经典）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 18:02
 * @description:抽象产品类
 */
public abstract class Product {
    //所有产品类的公共业务方法
    public void methodSame(){
        //公共方法的实现
    }

    //声明抽象业务方法
    public abstract void methodDiff();
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:05
 * @description:
 */
public class ConcreteProduct extends Product {
    //实现业务方法
    @Override
    public void methodDiff() {
        //业务方法的实现
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:08
 * @description:
 */
public class Factory {
    //静态工厂方法
    public static Product getProduct(String arg){
        Product product = null;
        if(arg.equalsIgnoreCase("A")){
            product = new ConcreteProduct();
            //初始化设值product
        }
        else if (arg.equalsIgnoreCase("B")) {
//            product = new ConcreteProductB();
            //初始化设置product
        }
        return product;
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:10
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Product product;
        product = Factory.getProduct("A");//通过工厂类创建产品对象
        product.methodSame();
        product.methodDiff();
    }
}
```

java实现代码（实例）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 18:15
 * @description:抽象产品类
 */
public interface Chart {
    public void display();
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:17
 * @description:饼图：具体产品类
 */
public class PieChart implements Chart{
    public PieChart() {
        System.out.println("创建饼图");
    }

    @Override
    public void display() {
        System.out.println("显示饼图");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:19
 * @description:
 */
public class LineChart implements Chart {
    public LineChart() {
        System.out.println("创建折线图");
    }

    @Override
    public void display() {
        System.out.println("显示折线图");
    }
}

public class HistogramChart implements Chart {

    public HistogramChart() {
        System.out.println("创建柱状图");
    }

    @Override
    public void display() {
        System.out.println("显示柱状图");
    }
}

**
 * @author Satsuki
 * @time 2019/6/9 18:20
 * @description:图表工厂类：工厂类
 */
public class ChartFactory {
    //静态工厂方法
    public static Chart getChart(String type){
        Chart chart = null;
        if(type.equalsIgnoreCase("histogram")){
            chart = new HistogramChart();
            System.out.println("初始化设值柱状图");
        }
        else if (type.equalsIgnoreCase("pie")) {
            chart = new PieChart();
            System.out.println("初始化设置饼状图！");
        }
        else if (type.equalsIgnoreCase("line")) {
            chart = new LineChart();
            System.out.println("初始化设置折线图！");
        }
        return chart;
    }
}

public class Client {
    public static void main(String[] args) {
        Chart chart;
        chart = ChartFactory.getChart("histogram");//通过静态工厂方法创建产品
        chart.display();
    }
}
```

### 工厂方法模式（Factory Method Pattern)

UML图解（类图与时序图）

![1563870790341](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563870790341.png)

类图

![1563870812424](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563870812424.png)

时序图

java实现代码（经典）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 18:02
 * @description:抽象产品类
 */
public abstract class Product {
    //所有产品类的公共业务方法
    public void methodSame(){
        //公共方法的实现
    }

    //声明抽象业务方法
    public abstract void methodDiff();
}

public class ConcreteProduct extends Product {
    //实现业务方法
    @Override
    public void methodDiff() {
        //业务方法的实现
    }
}
public interface Factory {
    public Product factoryMethod();

}

public class ConcreteFactory implements Factory {
    @Override
    public Product factoryMethod() {
//        return new ConcreteProduct();
        return new Product() {
            @Override
            public void methodDiff() {

            }
        };
    }


}
```

java实现代码（实例）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 18:42
 * @description:日志记录其接口：抽象产品
 */
public interface Logger {
    public void writeLog();
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:42
 * @description:数据库日志记录器：具体产品
 */
public class DatabaseLogger implements Logger{
    @Override
    public void writeLog() {
        System.out.println("数据库日志记录");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:43
 * @description:文件日志记录器：具体产品
 */
public class FileLogger implements Logger {
    @Override
    public void writeLog() {
        System.out.println("文件日志记录");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:43
 * @description:日志记录器工厂接口：抽象工厂
 */
public interface LoggerFactory {
    public Logger createLogger();
}

/**
 * @author Satsuki
 * @time 2019/6/9 18:44
 * @description:数据库日志记录器工厂类：具体工厂
 */
public class DatabaseLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        //连接数据库，代码省略
        //创建数据库日志记录器对象
        Logger logger = new DatabaseLogger();
        return logger;
    }

}
public class Client {
    public static void main(String[] args) {
        LoggerFactory factory;
        Logger logger;
        factory = new FileLoggerFactory();
        logger = factory.createLogger();
        logger.writeLog();
    }
}
```

### 抽象工厂模式（Abstract Factory Pattern)

UML图解（类图与时序图）

![1563873336675](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563873336675.png)

类图

![1563874312723](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1563874312723.png)

时序图

java实现代码（经典）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 18:58
 * @description:
 */
public class AbstractFactory {
//    public abstract AbstractProductA createProductA();//工厂方法1
//    public abstract AbstractProductA createProductA();//工厂方法2
}

public class ConcreateFactory1 {
//    //工厂方法一
//    public AbstractProductA createProductA() {
//        return new ConcreteProductA1();
//    }
//
//    //工厂方法二
//    public AbstractProductB createProductB() {
//        return new ConcreteProductB1();
//    }
}
```

java实现代码（实例）

```java
/**
 * @author Satsuki
 * @time 2019/6/9 19:04
 * @description:文本框接口：抽象产品
 */
public interface TextField {
    public void display();
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:06
 * @description:组合框接口：抽象产品
 */
public interface ComboBox {
    public void display();
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:02
 * @description:按钮接口：抽象产品
 */
public interface Button {
    public void display();
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:07
 * @description:界面皮肤工厂接口：抽象工厂
 */
public interface SkinFactory {
    public Button createButton();
    public TextField createTextField();
    public ComboBox createComboBox();
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:04
 * @description:Spring文本框类：具体产品
 */
public class SpringTextField implements TextField {
    @Override
    public void display() {
        System.out.println("显示绿色边框文本框。");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:06
 * @description:Spring组合框类：具体产品
 */
public class SpringComboBox implements ComboBox {
    @Override
    public void display() {
        System.out.println("显示绿色边框组合框。");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:03
 * @description:Spring按钮类：具体产品
 */
public class SpringButton implements Button {
    @Override
    public void display() {
        System.out.println("显示浅绿色按钮");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:08
 * @description:Spring皮肤工厂：具体工厂
 */
public class SpringSkinFactory implements SkinFactory {
    @Override
    public Button createButton() {
        return new SpringButton();
    }

    @Override
    public TextField createTextField() {
        return new SpringTextField();
    }

    @Override
    public ComboBox createComboBox() {
        return new SpringComboBox();
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:05
 * @description:Summer文本框类：具体产品
 */
public class SummerTextField implements TextField {
    @Override
    public void display() {
        System.out.println("显示蓝色边框文本框。");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:07
 * @description:Summer组合框类：具体产品
 */
public class SummerComboBox implements ComboBox {
    @Override
    public void display() {
        System.out.println("显示蓝色边框组合框。");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:03
 * @description:Summer按钮类：具体产品
 */
public class SummerButton implements Button {
    @Override
    public void display() {
        System.out.println("显示浅蓝色按钮");
    }
}

/**
 * @author Satsuki
 * @time 2019/6/9 19:09
 * @description:Summer皮肤工厂：具体工厂
 */
public class SummerSkinFactory implements SkinFactory {
    @Override
    public Button createButton() {
        return new SummerButton();
    }

    @Override
    public TextField createTextField() {
        return new SummerTextField();
    }

    @Override
    public ComboBox createComboBox() {
        return new SummerComboBox();
    }
}


public class client {
    public static void main(String[] args) {
        //使用抽象层定义
        SkinFactory factory;
        Button bt;
        TextField tf;
        ComboBox cb;
        factory = new SpringSkinFactory();
        bt = factory.createButton();
        tf = factory.createTextField();
        cb = factory.createComboBox();
        bt.display();
        tf.display();
        cb.display();
    }
}
```

## Structural Pattern

### java代理模式（proxy_parrern)

代理模式：给某一个对象提供一个代理或占位符，并由代理对象来控制对源对象的访问。

Proxy Pattern:Provide a surrogate or placeholder for another object to control access to it.

代理模式分为静态代理和动态代理

动态代理又分为两种实现方法：使用java提供的工具或者导入cglib.jar包

代理模式图示：

![1568538617593](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1568538617593.png)

静态代理模式有哪些角色

Subject(抽象主题角色):它声明了真实主题和代理主题的共同接口，这样依赖在任何使用真实主题的地方都可以使用代理主题，客户端通常需要针对抽象主题角色进行编程。

Porxy(代理主题角色):它包含了对真实主题的引用，从而可以在任何时候操作真实主题对象；在代理主题角色中提供了一个与真是主题角色相同的接口，以便在任何时候都可以替代真实主题；代理主题角色还可以控制对真实主题的使用，负责在需要的时候创建和删除真实主题角色，并对真实主题角色加以约束。通常，在代理主题角色中，客户端在调用所引用的真是主题操作之前或之后还需要执行其他操作，而不仅仅是单纯调用真实主题对象中的操作。

RealSubject(真实主题角色)：它定义了代理角色所代表的真实对象，在真实主题角色中实现了真实的业务操作，客户端可以通过代理主题角色简洁调用真实主题角色中定义的操作。



动态代理和静态代理的关系

与静态代理列对照的是动态代理类，动态代理类的字节码在程序运行时由java反射机制动态生成，无需程序员手工编写它的原代码。动态代理类不仅简化了编程工作而且提高了软件系统的扩展性，因为java反射机制可以生成任意类型的动态代理类。

#### 代理模式一般分为静态代理和动态代理

#### 静态代理

**静态代理类图：**

![1565603355209](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1565603355209.png)

java经典代码：

```java
/**
 * @author Satsuki
 * @time 2019/5/19 14:28
 * @description:
 */
public abstract class Subject {
    public abstract void Request();
}

public class RealSubject extends Subject {
    @Override
    public void Request() {
        //业务方法具体实现代码
    }
}

public class Proxy extends Subject {
    //维持一个对真实主题对象的引用
    private RealSubject realSubject = new RealSubject();

    public void PreRequest(){

    }

    @Override
    public void Request() {
        PreRequest();
        realSubject.Request();
        PostRequest();
    }
    public void PostRequest(){

    }
}
```

具体实现：

```java
/**
 * @author Satsuki
 * @time 2019/5/19 14:45
 * @description:抽象查询类
 */
public interface Search {
    public String doSearch(String userId,String keyword);
}
/**
 * @author Satsuki
 * @time 2019/5/19 14:46
 * @description:具体查询类
 */
public class RealSearcher implements Search {
    @Override
    public String doSearch(String userId, String keyword) {
        System.out.println("用户"+userId+"使用关键词"+keyword+"查询商务信息");
        return "返回具体内容";
    }
}

public class ProxySearcher implements Search{
    //维持一共对真实主题的引用
    private RealSearcher realSearcher = new RealSearcher();
    private AccessValidator accessValidator;
    private Logger logger;

    @Override
    public String doSearch(String userId, String keyword) {
        //如果身份验证成功则执行查询
        if(this.validate(userId)){
            //调用真实对象的查询方法
            String result = realSearcher.doSearch(userId,keyword);
            //记录查询日志
            this.log(userId);
            //返回查询结果
            return result;
        }
        return null;
    }

    //创建验证对象并调用其validate()方法进行身份验证
    public boolean validate(String userId){
        accessValidator = new AccessValidator();
        return accessValidator.validate(userId);
    }

    //创建日志记录对象并调用其log()方法实现日志记录
    public void log(String userId){
        logger = new Logger();
        logger.log(userId);
    }
}
/**
 * @author Satsuki
 * @time 2019/5/19 14:43
 * @description:日志记录
 */
public class Logger {
    //模拟实现日志记录
    public void log(String userId){
        System.out.println("更新数据库，用户"+userId+"查询次数加1");
    }
}
/**
 * @author Satsuki
 * @time 2019/5/19 14:39
 * @description:身份验证类提供validate()方法来实现身份验证
 */
public class AccessValidator {
    //模拟实现登陆验证
    public boolean validate(String userId){
        System.out.println("在数据库中验证用户" + userId + "是否为合法用户");
        if(userId.equalsIgnoreCase("yukari")){
            System.out.println(userId+"登陆成功");
            return true;
        }else {
            System.out.println(userId + "登陆失败");
            return false;
        }
    }
}

public class Client {
    public static void main(String[] args){
        //针对抽象编程，客户端无需分辨真实类和代理类
        Search search;
        search = new ProxySearcher();
        String result = search.doSearch("yukari","yuyuko");
    }
}
```

#### 动态代理:



```java
/**
 * @author Satsuki
 * @time 2019/5/19 15:04
 * @description:
 */
public interface AbstractDocumentDAO {
    public Boolean deleteDocumentById(String documentId);
}

public interface AbstractUserDAO {
    public Boolean findUserById(String userId);
}

public class DocumentDAO implements AbstractDocumentDAO {
    @Override
    public Boolean deleteDocumentById(String documentId) {
        if(documentId.equalsIgnoreCase("D001")){
            System.out.println("delete document with ID" + documentId + "success");
            return true;
        }else {
            System.out.println("delete document with ID" + documentId + "failure");
            return false;
        }
    }
}

public class UserDAO implements AbstractUserDAO {
    @Override
    public Boolean findUserById(String userId) {
        if(userId.equalsIgnoreCase("Satsuki")){
            System.out.println("查询ID"+userId+"的用户信息成功");
            return true;
        }else {
            System.out.println("查询ID"+userId+"的用户信息失败");
            return false;
        }
    }
}

/**
 * @author Satsuki
 * @time 2019/5/19 15:11
 * @description:自定义请求处理程序类
 */
public class DAOLogHandler  implements InvocationHandler {
    private Calendar calendar;
    private Object object;
    public DAOLogHandler(){

    }
    //自定义构造函数，用于注入一共需要提供代理的真实对象
    public DAOLogHandler(Object object){
        this.object = object;
    }
    //实现invoke
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeInvoke();
        Object result = method.invoke(object,args);//转发调用
        afterInvoke();
        return result;
    }

    //记录方法调用时间
    public void beforeInvoke(){
        calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String time = hour + ":" + minute + ":" + second;
        System.out.println("调用时间"+time);
    }
    public void afterInvoke(){
        System.out.println("方法调用结束");
    }
}

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Satsuki
 * @time 2019/5/19 15:19
 * @description:
 */
public class Client {
    public static void main(String[] args){
        InvocationHandler handler = null;
        AbstractUserDAO userDAO = new UserDAO();
        handler = new DAOLogHandler(userDAO);
        AbstractUserDAO proxy=null;

        //动态创建代理对象，用于代理一个AbstractUserDAO类型的真实对象
        proxy = (AbstractUserDAO) Proxy.newProxyInstance(AbstractUserDAO.class.getClassLoader()
                ,new Class[]{AbstractUserDAO.class},handler);
        //调用代理对象的业务方法
        proxy.findUserById("Satsuki");
        System.out.println("-------------------------------------------------");

        AbstractDocumentDAO docDAO = new DocumentDAO();
        handler = new DAOLogHandler(docDAO);
        AbstractDocumentDAO proxy_new=null;

        //动态创建代理对象，用于代理一个AbstractDocumentDAO类型的真实对象
        proxy_new = (AbstractDocumentDAO)Proxy.newProxyInstance(AbstractDocumentDAO.class.getClassLoader(),new Class[]{AbstractDocumentDAO.class},handler);
        proxy_new.deleteDocumentById("D002");//调用代理对象的业务方法

    }
}

```

cglib实现:

```java

/**
 * @author Satsuki
 * @time 2019/8/17 17:27
 * @description:
 */
public interface Gongneng {
    void chifan();
    void mubiao();
}

public class Laozong implements Gongneng {
    @Override
    public void chifan() {
        System.out.println("chifan");
    }

    @Override
    public void mubiao() {
        System.out.println("mubiao");
    }
}

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/8/17 17:29
 * @description:
 */
public class Mishu implements InvocationHandler {
    private Laozong laozong = new Laozong();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("预约时间");
        Object result = method.invoke(laozong, args);
        System.out.println("记录信息");
        return result;
    }
}

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/8/17 17:40
 * @description:
 */
public class Xiaomi implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("预约时间");
//        method.invoke(o,objects);
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("备注");
        return result;
    }
}

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Satsuki
 * @time 2019/8/17 17:32
 * @description:
 */
public class Women {
    public static void main(String[] args) {
        //JDK动态代理
//        Mishu mishu = new Mishu();
//        Gongneng gongneng = (Gongneng)Proxy.newProxyInstance(Women.class.getClassLoader(), new Class[]{Gongneng.class}, mishu);
//        gongneng.chifan();

        //CGLIB
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Laozong.class);
        enhancer.setCallback(new Xiaomi());

        Laozong laozong = (Laozong)enhancer.create();
        laozong.chifan();
    }
}
```



### 桥接模式（Bridge Pattern)

核心：处理多层继承结构，处理多维度变化的场景，将各个维度设计成独立的继承结构，使各个维度可以独立的扩展在抽象层建立关联。

![1565690897092](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1565690897092.png)

总结：

桥接模式可以取代多层继承的方案。多层继承违背了单一指责原则，复用性较差，类的个数也非常多。桥接模式可以极大的减少子类的数量，从而降低管理和维护的成本。

桥接模式极大的提高了系统的可扩展性，在两个变化维度中任意扩展一个维度，都不需要修改原有的系统，符合开闭原则。

就像一座桥，将两个变化维度连接起来，各个维度都可以独立的变化，故称之为：桥模式。

classic

```java
/**
 * @author Satsuki
 * @time 2019/8/13 17:42
 * @description:在实现Implementor接口的子类中实现了在该接口中声明的方法，
 * 用于定义与该维度相对应的一些具体方法。
 */
public interface Implementor {
    public void operationImpl();
}

public class ConcreteImplementor implements Implementor {
    @Override
    public void operationImpl() {
        //具体业务方法的实现
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:43
 * @description:对于另一“抽象部分”维度而言，其典型的抽象类代码如下所示：
 */
public abstract class Abstraction {
    //定义实现类的抽象接口给
    protected Implementor impl;

    public Abstraction(Implementor impl) {
        this.impl = impl;
    }

    public abstract void operation();
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:45
 * @description:
 * 在抽象类Abstraction中定义了一个实现类接口类型的成员对象impl，
 * 再通过注入的方式给该对象赋值，一般将该对象的可见性定义为protected，
 * 以便在其子类中访问Implementor的方法，
 * 其子类一般称为扩充抽象类或细化抽象类(RefinedAbstraction)，
 * 典型的RefinedAbstraction类代码如下所示：
 */
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor impl) {
        super(impl);
    }

    @Override
    public void operation() {
        //业务代码
        //调用实现类的方法
        impl.operationImpl();
        //业务代码
    }
}
```

example

```java
/**
 * @author Satsuki
 * @time 2019/8/13 17:52
 * @description:
 * 抽象操作系统实现类：实现类接口
 */
public interface ImageImp {
    //显示像素矩阵m
    public void doPaint(Matrix m);
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:54
 * @description:
 * Windows操作系统实现类：具体实现类
 */
public class WindowImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Windows系统的绘制函数绘制像素矩阵
        System.out.println("在windows操作系统中显示图像");
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:55
 * @description:
 * //Unix操作系统实现类：具体实现类
 */
public class UnixImp implements ImageImp {
    public void doPaint(Matrix m) {
        //调用Unix系统的绘制函数绘制像素矩阵
        System.out.print("在Unix操作系统中显示图像：");
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:54
 * @description:
 * Linux操作系统实现类：具体实现类
 */
public class LinuxImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Linux系统的绘制函数绘制像素矩阵
        System.out.print("在Linux操作系统中显示图像：");

    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:51
 * @description:
 * 抽象图像类：抽象类
 */
public abstract class Image {
    protected ImageImp imageImp;


    public void setImageImp(ImageImp imageImp) {
        this.imageImp = imageImp;
    }

    public abstract void parseFile(String fileName);
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:58
 * @description:
 * BMP格式图像：扩充抽象类
 */
public class BMPImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析BMP文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为BMP。");
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:58
 * @description:
 * GIF格式图像：扩充抽象类
 */
public class GIFImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析GIF文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为GIF。");
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:56
 * @description:
 * JPG格式图像：扩充抽象类
 */
public class JPGImage extends Image {
    @Override
    public void setImageImp(ImageImp imageImp) {
        super.setImageImp(imageImp);
    }

    @Override
    public void parseFile(String fileName) {
        //模拟解析JPG文件并获得一个像素矩阵对象m
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName+ ",格式为jpg");

    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:57
 * @description:/
 * NG格式图像：扩充抽象类
 */
public class PNGImage extends Image {
    public void parseFile(String fileName) {
        //模拟解析PNG文件并获得一个像素矩阵对象m;
        Matrix m = new Matrix();
        imageImp.doPaint(m);
        System.out.println(fileName + "，格式为PNG。");
    }
}

/**
 * @author Satsuki
 * @time 2019/8/13 17:59
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Image image = new JPGImage();
        ImageImp imageImp = new WindowImp();
        image.setImageImp(imageImp);
        image.parseFile("satsuki");
    }
}
```

1