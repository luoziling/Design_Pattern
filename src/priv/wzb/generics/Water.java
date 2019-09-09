package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:21
 * @description:
 */
public class Water {
    private String name;

    public Water(String name) {
        this.name = name;
    }

    public void sayName(){
        System.out.println("name===:" + name);
    }

    public String getName(){
        return name;
    }
}
