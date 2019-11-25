package priv.wzb.interview.test;

/**
 * @author Satsuki
 * @time 2019/11/12 17:19
 * @description:
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("A");

        new Main();

        new Main();

    }



    public Main() {

        System.out.println("B");

    }



    {

        System.out.println("C");

    }



    static {

        System.out.println("D");

    }
}

