package priv.wzb.design_pattern.createdpattern.factorymethodpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:42
 * @description:数据库日志记录器：具体产品
 */
public class DatabaseLogger implements Logger{
    @Override
    public void writeLog() {
        System.out.println("数据库日志记录");
    }
}
