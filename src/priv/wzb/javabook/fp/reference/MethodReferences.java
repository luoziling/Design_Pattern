package priv.wzb.javabook.fp.reference;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 17:40
 **/

public class MethodReferences {
    static void hello(String name){
        System.out.println("Hello, " + name);
    }
    static class Description{
        String about;
        Description(String desc){about = desc;}
        void help(String msg){
            System.out.println(about + " " + msg);
        }
    }
    static class Helper{
        static void assist(String msg){
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
        Describe d = new Describe();
        Callable c = d::show;

        c.call("call()");

        c= MethodReferences::hello;
        c.call("Bob");

        c = new Description("valuable")::help;
        c.call("information");

        c=Helper::assist;
        c.call("Help!");
    }
}
