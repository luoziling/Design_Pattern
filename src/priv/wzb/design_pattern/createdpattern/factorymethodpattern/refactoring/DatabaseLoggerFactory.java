package priv.wzb.design_pattern.createdpattern.factorymethodpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:44
 * @description:数据库日志记录器工厂类：具体工厂
 */
public class DatabaseLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        //连接数据库，代码省略
        //创建数据库日志记录器对象
        Logger logger = new DatabaseLogger();
        return logger;
    }

}
