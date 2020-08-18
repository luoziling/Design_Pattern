package priv.wzb.javabook.exceptions;

/**
 * @author Satsuki
 * @time 2020/8/17 22:37
 * @description:
 */
public class Reporter implements AutoCloseable {
    String name = getClass().getSimpleName();

    public Reporter() {
        System.out.println("Creating" + name);
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing" + name);
    }
}
class First extends Reporter{

}
class Second extends Reporter{

}