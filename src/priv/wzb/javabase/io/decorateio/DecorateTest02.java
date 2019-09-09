package priv.wzb.javabase.io.decorateio;

/**
 * @author Satsuki
 * @time 2019/9/5 16:25
 * @description:
 * 模拟咖啡
 * 1.抽象组件：需要装饰的抽象对象（接口或抽象父类）
 * 2.具体组件：需要装饰的对象
 * 3.抽象装饰类：包含了对抽象组件的引用以及装饰着其公有方法
 * 4.具体装饰类:被装饰的对象
 *
 */
public class DecorateTest02 {
    public static void main(String[] args) {
        Drink coffee = new Coffee();
        Drink sugar = new Sugar(coffee);
        Drink sugar1 = new Sugar(new Milk(coffee));
        System.out.println(coffee.info());
        System.out.println(sugar.info());
        System.out.println(sugar1.info());
    }

}
//抽象组件
interface Drink{
    double cost();
    String info();
}

//具体组件
class Coffee implements Drink{
    private String name="原味coffee";
    @Override
    public double cost() {
        return 10;
    }

    @Override
    public String info() {
        return name;
    }
}

//抽象装饰类
abstract class Decorate implements Drink{
    //对抽象组件的引用
    private Drink drink;

    public Decorate(Drink drink) {
        this.drink = drink;
    }

    @Override
    public double cost() {
        return this.drink.cost();
    }

    @Override
    public String info() {
        return this.drink.info();
    }
}

//具体装饰类
class Milk extends Decorate{


    public Milk(Drink drink) {
        super(drink);
    }

    @Override
    public double cost() {
        return super.cost()*4;
    }

    @Override
    public String info() {
        return super.info()+"add milk";
    }
}

class Sugar extends Decorate{
    public Sugar(Drink drink) {
        super(drink);
    }

    @Override
    public double cost() {
        return super.cost()*2;
    }

    @Override
    public String info() {
        return super.info()+ "add sugar";
    }
}
