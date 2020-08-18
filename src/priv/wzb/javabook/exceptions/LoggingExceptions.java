package priv.wzb.javabook.exceptions;

/**
 * @author Satsuki
 * @time 2020/8/17 21:43
 * @description:
 */
public class LoggingExceptions {
    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught" + e);
        }

        try {
            throw new LoggingException();
        } catch(LoggingException e) {
            System.err.println("Caught " + e);
        }

        try {
            int a = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
