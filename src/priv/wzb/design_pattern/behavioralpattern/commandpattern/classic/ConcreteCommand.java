package priv.wzb.design_pattern.behavioralpattern.commandpattern.classic;

/**
 * @author Satsuki
 * @time 2019/5/23 17:30
 * @description:
 */
public class ConcreteCommand extends Command {
    //维持一共对请求接收者对象的引用
    private Receiver receiver;
    @Override
    public void execute() {
        //调用请求接收者的业务处理方法action()
        receiver.action();
    }
}
