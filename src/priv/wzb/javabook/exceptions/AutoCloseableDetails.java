package priv.wzb.javabook.exceptions;

/**
 * @author Satsuki
 * @time 2020/8/17 22:38
 * @description:
 */
public class AutoCloseableDetails {
    public static void main(String[] args) {
        try(
                First f = new First();
                Second s = new Second()
        ) {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
