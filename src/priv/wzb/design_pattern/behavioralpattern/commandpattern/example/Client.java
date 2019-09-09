package priv.wzb.design_pattern.behavioralpattern.commandpattern.example;

/**
 * @author Satsuki
 * @time 2019/5/23 17:44
 * @description:
 */
public class Client {
    public static void main(String[] args){
        FunctionButton fb = new FunctionButton();
        Command command = new ExitCommand();
        fb.setCommand(command);
        fb.click();
    }
}
