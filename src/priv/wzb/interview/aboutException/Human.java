package priv.wzb.interview.aboutException;

/**
 * @author Satsuki
 * @time 2019/7/8 13:29
 * @description:
 */
public class Human {
    public static void main(String[] args) throws Exception {
        try {
            try {
                throw new Sneeze();
            }catch (Annoyance a){
                System.out.println("Caught Annoyance");
                throw a;
            }
        }catch (Sneeze s){
            System.out.println("Caught Sneeze");
        }finally {
            System.out.println("Hello World!");
        }
    }
}
