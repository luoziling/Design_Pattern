package priv.wzb.design_pattern.createdpattern.factorymethodpattern.refactoring;



/**
 * @author Satsuki
 * @time 2019/6/9 18:45
 * @description:文件日志记录器工厂类：具体工厂
 */
public class FileLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        //创建文件日志记录器对象
        Logger logger = new FileLogger();
        //创建文件，代码省略
        return logger;
    }
}
