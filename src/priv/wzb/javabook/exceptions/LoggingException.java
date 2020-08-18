package priv.wzb.javabook.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * @author Satsuki
 * @time 2020/8/17 21:40
 * @description:
 */
public class LoggingException extends Exception {
    private static Logger logger =
            Logger.getLogger("LoggingException");

    public LoggingException() {
        StringWriter trace = new StringWriter();
        printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString());
    }
}
