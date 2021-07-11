package priv.wzb.multi_threaded_high_concurrent.threadlocal.message;

/**
 * ConcreteStrategy1
 *
 * @author yuzuki
 * @date 2021/7/11 16:55
 * @description:
 * @since 1.0.0
 */
public class ConcreteStrategy1 implements NoticeStrategy{
    private String s1;

    @Override
    public void init(String initStr) {
        this.s1 = initStr;
    }

    @Override
    public String get() {
        return s1;
    }

    @Override
    public String uniform(String initStr) {
        this.s1 = initStr;
        return s1;
    }
}
