# java反射

Class对象

利用Class对象，我们几乎能将一个对象的祖宗十八代都调查出来。

获得类信息

读取操作 属性方法构造器

反射会降低程序运行效率

setAccessible（提高反射效率4倍

反射会降低运行效率（降低30倍调用方法效率）





# 运行期类型鉴定（RTTI RunTimeType Identify)

手上只有一个基础类型的句柄时判断一个对象的正确类型

RTTI鉴定我们已在编译和运行期间拥有所有类型

另一种是反射

利用它可以在运行期独立查找类信息



```java
//: Shapes.java
package c11;
import java.util.*;

interface Shape {
  void draw();
}

class Circle implements Shape {
  public void draw() {
    System.out.println("Circle.draw()");
  }
}

class Square implements Shape {
  public void draw() {
    System.out.println("Square.draw()");
  }
}

class Triangle implements Shape {
  public void draw() {
    System.out.println("Triangle.draw()");
  }
}

public class Shapes {
  public static void main(String[] args) {
    Vector s = new Vector();
    s.addElement(new Circle());
    s.addElement(new Square());
    s.addElement(new Triangle());
    Enumeration e = s.elements();
    while(e.hasMoreElements())
      ((Shape)e.nextElement()).draw();
  }
} ///:~
```

**RTTI的意义所在：在运行期，对象的类型会得到鉴定。**

用nextElement()将一个元素从Vector提取出来的时候，情况变得稍微有些复杂。由于Vector只容纳Object，所以nextElement()会自然地产生一个Object句柄。但我们知道它实际是个Shape句柄，而且希望将Shape消息发给那个对象。所以需要用传统的"(Shape)"方式造型成一个Shape。这是RTTI最基本的形式，因为在Java中，所有造型都会在运行期间得到检查，以确保其正确性。那正是RTTI的意义所在：在运行期，对象的类型会得到鉴定。



在C++中，经典的"(Shape)"造型并不执行RTTI。它只是简单地告诉编译器将对象当作新类型处理。而Java要执行类型检查，这通常叫作“类型安全”的下溯造型。之所以叫“下溯造型”，是由于类分层结构的历史排列方式造成的。若将一个Circle（圆）造型到一个Shape（几何形状），就叫做上溯造型，因为圆只是几何形状的一个子集。反之，若将Shape造型至Circle，就叫做下溯造型。然而，尽管我们明确知道Circle也是一个Shape，所以编译器能够自动上溯造型，但却不能保证一个Shape肯定是一个Circle。因此，编译器不允许自动下溯造型，除非明确指定一次这样的造型。

**下溯造型 Shape->Circle不安全**

**任何使用子类的地方可以用父类代替反之则不行**

# 