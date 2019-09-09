package priv.wzb.design_pattern.structural_pattern.adapterpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/18 17:45
 * @description:
 */
public abstract class CarController {
    public void move(){
        System.out.println("玩具汽车移动！");
    }
    public abstract void phonate();//发出声音
    public abstract void twinkle();//灯光闪烁
}
