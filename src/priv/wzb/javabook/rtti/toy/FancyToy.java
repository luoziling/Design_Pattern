package priv.wzb.javabook.rtti.toy;

/**
 * @author Satsuki
 * @time 2019/9/12 16:49
 * @description:
 */
public class FancyToy extends Toy implements HasBatteries,Waterproof,ShootsThings {
    public FancyToy(int i) {
        super(i);
    }
}
