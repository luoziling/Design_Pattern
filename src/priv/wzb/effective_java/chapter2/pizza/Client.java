package priv.wzb.effective_java.chapter2.pizza;

/**
 * @author Satsuki
 * @time 2019/6/25 15:37
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        NyPizza pizza =(NyPizza) new NyPizza.Builder(NyPizza.Size.SMALL).addTopping(Pizza.Topping.SAUSAGE).addTopping(Pizza.Topping.ONION).build();
        Calzone calzone = (Calzone) new Calzone.Builder().addTopping(Pizza.Topping.HAM).sauceInside().build();
        System.out.println(pizza.toppings);
        System.out.println(calzone.toppings);
        System.out.println(calzone.toString());
    }
}
