package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:01
 **/

/**
 * Class 'Basic2' must either be declared abstract
 * or implement abstract method 'unimplemented()' in 'Basic'
 */
public abstract class Basic2 extends Basic {
    int f() {
        return 111;
    }

    /**
     * Abstract methods cannot have a body
     */
//    abstract void g() {
//        // unimplemented() still not implemented
//    }
}
