package priv.wzb.design_pattern.behavioralpattern.commandpattern.example;

/**
 * @author Satsuki
 * @time 2019/5/23 17:44
 * @description:
 */
public class FunctionButton {
    //维持一共抽象命令对象的引用
    private Command command;
    //为功能键注入命令
    public void setCommand(Command command){
        this.command = command;
    }
    //发送请求的方法
    public void click(){
        System.out.println("单机功能键！");
        command.execute();
    }
}
