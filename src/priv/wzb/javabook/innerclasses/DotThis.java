package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 16:01
 **/

public class DotThis {
    void f(){
        System.out.println("DotThis.f");
    }

    public class Inner{
        public DotThis outer(){
            return DotThis.this;
            // A plain "this" would be Inner's "this"
        }
    }

    public Inner inner(){
        return new Inner();
    }

    public static void main(String[] args) {
        DotThis dt = new DotThis();
        DotThis.Inner dti = dt.inner();
        dti.outer().f();
        dti = dt.new Inner();
    }
}
