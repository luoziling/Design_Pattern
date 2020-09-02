package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 19:14
 **/
interface Incrementable{
    void increment();
}
// very simple to just implement the interface
class Callee1 implements Incrementable{
    private int i = 0;

    @Override
    public void increment() {
        i++;
        System.out.println(i);
    }
}

class MyIncrement {
    public void increment() {
        System.out.println("Other operation");
    }
    static void f(MyIncrement mi) { mi.increment(); }
}

// 如果必须implement increment()方法可以用内部类试试
class Callee2 extends MyIncrement{
    private int i = 0;

    @Override
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }

    private class Closure implements Incrementable{
        @Override
        public void increment() {
            Callee2.this.increment();
        }
    }
    Incrementable getCallbackReference(){
        return new Closure();
    }
}

class Caller{
    private Incrementable callbackReference;

    public Caller(Incrementable callbackReference) {
        this.callbackReference = callbackReference;
    }

    void go(){callbackReference.increment();}
}

public class Callbacks {
    public static void main(String[] args) {
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f(c2);
        Caller caller1 = new Caller(c1);
        Caller caller2 =
                new Caller(c2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }
}
