package priv.wzb.javabook.typeinfo;

import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-08 17:49
 * @description:
 **/

abstract class Shape{
    void drow(){
        System.out.println(this + ".draw()");
    }

    @Override
    public abstract String toString();
}

class Circle extends Shape {
    @Override
    public String toString() { return "Circle"; }
}

class Square extends Shape {
    @Override
    public String toString() { return "Square"; }
}

class Triangle extends Shape {
    @Override
    public String toString() { return "Triangle"; }
}


public class Shapes {
    public static void main(String[] args) {
        Stream.of(
                new Circle(),new Square(),new Triangle()
        ).forEach(Shape::drow);
    }

}
