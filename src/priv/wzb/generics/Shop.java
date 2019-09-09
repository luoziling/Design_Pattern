package priv.wzb.generics;

/**
 * @author Satsuki
 * @time 2019/6/11 15:19
 * @description:
 */
public class Shop<T> {
    private T t;

    public Shop(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
