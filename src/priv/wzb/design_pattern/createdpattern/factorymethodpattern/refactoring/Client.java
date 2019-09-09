package priv.wzb.design_pattern.createdpattern.factorymethodpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:46
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        LoggerFactory factory;
        Logger logger;
        factory = new FileLoggerFactory();
        logger = factory.createLogger();
        logger.writeLog();
    }
}
