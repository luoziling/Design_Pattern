package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:43
 * @description:日志记录
 */
public class Logger {
    //模拟实现日志记录
    public void log(String userId){
        System.out.println("更新数据库，用户"+userId+"查询次数加1");
    }
}
