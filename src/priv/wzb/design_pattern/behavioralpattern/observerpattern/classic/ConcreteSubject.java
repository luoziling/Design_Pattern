package priv.wzb.design_pattern.behavioralpattern.observerpattern.classic;

//import java.util.Observer;

/**
 * @author Satsuki
 * @time 2019/4/7 14:22
 * @description:
 */
public class ConcreteSubject extends Subject {
    //实现通知方法
    @Override
    public void notifies() {
        //遍历观察者集合，调用每一个观察者的响应方法
        for (Object obs : observers) {
//            ((Observer)obs).update();
            ((Observer) obs).update();
//            obs.equals();
        }
    }
}
