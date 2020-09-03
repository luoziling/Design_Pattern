package priv.wzb.javabook.validating;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-02 17:14
 **/

public class LoaderAssertions {
    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true);
        new Loaded().go();
    }
}

class Loaded{
    public void go(){
        assert false:"Loaded.go()";
    }
}
