package priv.wzb.javabook.rtti;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-11 15:46
 * @description:
 **/
interface Interface{
    void doSomething();
    void somethingElse(String arg);
}
class RealObject implements Interface{
    @Override
    public void doSomething() {
        System.out.println("RealObject.doSomething");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("somethingElse = " + arg);
    }
}
class SimpleProxy implements Interface{
    private Interface proxied;

    public SimpleProxy(Interface proxied) {
        this.proxied = proxied;
    }

    @Override
    public void doSomething() {
        System.out.println("SimpleProxy.doSomething");
        proxied.doSomething();
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("SimpleProxy.somethingElse" + arg);
        proxied.somethingElse(arg);
    }
}
public class SimpleProxyDemo {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
}
