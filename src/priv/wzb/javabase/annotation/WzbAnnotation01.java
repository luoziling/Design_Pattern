package priv.wzb.javabase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Satsuki
 * @time 2019/9/12 14:20
 * @description:
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WzbAnnotation01 {
    String stuentName() default "";

    int age() default 0;

    int id() default -1;

    String[] schools() default {"清华大学", "北京大学"};

}
