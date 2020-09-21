package priv.wzb.javabook.annotations;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-17 10:08
 * @description:
 **/
public @interface Uniqueness {
    Constraints constraints()
            default @Constraints(unique = true);
}
