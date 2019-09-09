package priv.wzb.design_pattern.behavioralpattern.commandpattern.example;

/**
 * @author Satsuki
 * @time 2019/5/23 17:38
 * @description:
 */
public class ExitCommand extends Command {
    //维持对请求接收者的引用
    private SystemExitClass seObj;
    public ExitCommand(){
        seObj = new SystemExitClass();
    }

    //命令执行方法，将调用请求接收者的业务方法
    @Override
    public void execute() {
        seObj.exit();
    }
}
