package priv.wzb.design_pattern.behavioralpattern.statepattern.classic;

/**
 * @author Satsuki
 * @time 2020/6/20 22:19
 * @description: 环境类
 * 环境类又称为上下文类，它是拥有多种状态的对象。
 * 由于环境类的状态存在多样性且在不同状态下对象的行为有所不同，
 * 因此将状态独立出去形成单独的状态类。在环境类中维护一个抽象状态类State的实例，
 * 这个实例定义当前状态，在具体实现时，它是一个State子类的对象。
 */
public class Context {
    //维持一个对抽象状态对象的引用
    private State state;
    // 其他属性值，该属性值的变化可能会导致对象状态发生变化
    private int value;

    // 设置对象状态
    public void setState(State state){
        this.state = state;
    }

    public void request(){
        // 其他代码
        // 调用状态对象的业务方法
        state.handle();
        // 其他代码
    }

    public State getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // 由环境类来负责状态之间的切换
    public void changeState(){
        // 判断属性值，根据属性值进行状态转换
        if (value == 0){
            this.setState(new ConcreteState());
        }else if (value == 1){
            // 假装切换至其他状态
//            this.setState(new ConcreteStateA());
        }
    }

}
