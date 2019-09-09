package priv.wzb.design_pattern.behavioralpattern.observerpattern.classic;

import java.util.ArrayList;
//import java.util.Observer;

/**
 * @author Satsuki
 * @time 2019/4/7 14:11
 * @description:
 */
//不声明就是friendly
//    friendly与protected的区别：
    //friendly在包内可以访问在包外无法访问
    //protected的话在包内可以访问在包外只能被集成它的子类访问
abstract class Subject {

    //定义一个观察者集合用于存储所有观察者对象
    protected ArrayList<Observer> observers = new ArrayList<>();

    //注册方法用于向观察者集合中增加一个观察者
    public void attach(Observer observer){
        observers.add(observer);
    }

    //注销方法用于在观察者集合中删除一个观察者
    public void detach(Observer observer){
        observers.remove(observer);
    }

    //声明抽象通知方法
    //notify()' cannot override 'notify()' in 'java.lang.Object'; overridden method is final
    public abstract void notifies();

}
