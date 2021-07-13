package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 17:36
 **/

public class TestBed {
    public void f(){
        System.out.println("TestBed.f");
    }
    public static class Tester{
        public static void main(String[] args) {
            TestBed t = new TestBed();
            t.f();
        }
    }

    public static void main(String[] args) {
        TestBed t = new TestBed();
        t.f();
    }
}
