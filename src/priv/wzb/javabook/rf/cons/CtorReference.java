package priv.wzb.javabook.rf.cons;

/**
 * @author Satsuki
 * @time 2020/8/12 20:50
 * @description:
 */
public class CtorReference {
    public static void main(String[] args) {
        MakeNoArgs mna = Dog::new;
        Make1Arg m1a = Dog::new;
        Make2Args m2a = Dog::new;

        Dog dn = mna.make();
        Dog d1 = m1a.make("Comet");
        Dog d2 = m2a.make("Ralph",4);
    }
}
