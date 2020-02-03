package priv.wzb.design_pattern.createdpattern.prototypepattern.classic;

/**
 * @author Satsuki
 * @time 2020/2/3 14:15
 * @description:
 * 用于测试Java自带的Object对象中的clone
 */
public class MyPrototype implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        MyPrototype myPrototype = new MyPrototype();
        try {
            System.out.println(myPrototype.clone());

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
