package priv.wzb.generics;

import java.util.ArrayList;

/**
 * @author Satsuki
 * @time 2019/6/11 15:19
 * @description:
 */
public class TestClass {
    public static void main(String[] args) {
        Water water = new Water("纯净水");
        SodaDrink sodaDrink  = new SodaDrink("可乐");
        Juice myJuice = new Juice("果汁");
        OrangeJuice orangeJuice = new OrangeJuice("果粒橙");
        AppleJuice appleJuice = new AppleJuice("牛顿牌果汁");

        ArrayList<OrangeJuice> temList = new ArrayList<>();
        temList.add(orangeJuice);

        ArrayList<? extends Juice> arrayList1 = new ArrayList<Juice>();
        ArrayList<? extends Juice> arrayList3 = new ArrayList<OrangeJuice>();

//        arrayList1.add(myJuice);

        ArrayList<? extends Juice> arrayList4 = temList;
        Juice juice23 = arrayList4.get(0);
        System.out.println(juice23.getName());

        ArrayList<? super Juice> arrayList11 = new ArrayList<Juice>();
        ArrayList<? super Juice> arrayList22 = new ArrayList<Water>();

        arrayList11.add(juice23);
        arrayList11.add(orangeJuice);


        Object obj = arrayList11.get(0);
        Juice jui = (Juice) arrayList11.get(1);
        jui.sayName();
    }
}
