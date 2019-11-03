package priv.wzb.javabase.annotation;

/**
 * @author Satsuki
 * @time 2019/9/12 14:24
 * @description:
 */

@WzbAnnotation01
public class Demo02 {
    @WzbAnnotation01(age = 19,stuentName = "satsuki",id = 1001,schools = {"淮工","淮师"})
    public void test(){

    }


    @WzbAnnotation02("aaa")
    public void test2(){

    }

}
