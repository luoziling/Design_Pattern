package priv.wzb.javabase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Satsuki
 * @time 2019/9/12 14:53
 * @description:
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WzbField {
    String columnName();
    String type();
    int length();
}
