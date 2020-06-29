package priv.wzb.design_pattern.behavioralpattern.statepattern.classic;

/**
 * @author Satsuki
 * @time 2020/6/20 22:17
 * @description:
 * 具体状态类：它是抽象状态类的子类，每一个子类实现一个与环境类的一个状态相关的行为，
 * 每一个具体状态类对应环境的一个具体状态，不同的具体状态类其行为有所不同。
 */
public class ConcreteState extends State {
    @Override
    public void handle() {
        // 方法具体实现代码
    }
    // 由具体状态类负责状态之间的转换
    public void changeState(Context context){
        // 根据环境对象中的属性值进行状态切换
        if (context.getValue() == 1){
            context.setState(new ConcreteState());
        }else if (context.getValue()==2){
//            context.setState();
        }
    }
}
