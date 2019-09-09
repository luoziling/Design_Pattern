package priv.wzb.design_pattern.createdpattern.factorymethodpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 18:43
 * @description:文件日志记录器：具体产品
 */
public class FileLogger implements Logger {
    @Override
    public void writeLog() {
        System.out.println("文件日志记录");
    }
}
