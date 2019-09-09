package priv.wzb.effective_java.chapter2.article4;

/**
 * @author Satsuki
 * @time 2019/6/27 10:04
 * @description: noninstantiable utility class
 */
public class UtilityClass {
    // Suppress default constructor for noninstantiability

    public UtilityClass() {
        throw new AssertionError();
    }
    //Remainder omitted
}
