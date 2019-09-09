package priv.wzb.design_pattern.structural_pattern.flyweightpattern.runoob;

import java.util.HashMap;

/**
 * @author Satsuki
 * @time 2019/6/18 15:36
 * @description:
 */
public class ShapeFactory {
    private static final HashMap<String,Shape> circleMap = new HashMap<>();
    public static Shape getCircle(String color){
        Circle circle = (Circle)circleMap.get(color);
        if(circle == null){
            circle = new Circle(color);
            circleMap.put(color,circle);
            System.out.println("Creating Circle of color:" + color);
        }
        return circle;
    }
}
