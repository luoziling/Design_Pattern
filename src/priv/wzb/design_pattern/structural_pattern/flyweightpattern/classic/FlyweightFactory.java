package priv.wzb.design_pattern.structural_pattern.flyweightpattern.classic;

import java.util.HashMap;

/**
 * @author Satsuki
 * @time 2019/6/18 17:14
 * @description:
 */
public class FlyweightFactory {
    //定义一个HashMap用于存储享元对象，实现享元池
    private HashMap flyweights = new HashMap();
    public Flyweight getFlyweight(String key){
        //如果对象存在，则直接从享元池中获取
        if(flyweights.containsKey(key)){
            return (Flyweight)flyweights.get(key);
        }else {
            Flyweight fw = new ConcreteFlyweight();
            flyweights.put(key,fw);
            return fw;
        }
    }
}
