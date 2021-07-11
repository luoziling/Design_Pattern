package priv.wzb.multi_threaded_high_concurrent.threadlocal.message;

/**
 * NoticeStrategy
 *
 * @author yuzuki
 * @date 2021/7/11 16:54
 * @description:
 * @since 1.0.0
 */
public interface NoticeStrategy {
    /**
     * 对象初始化
     * @param initStr
     */
    void init(String initStr);

    /**
     * 资源操作
     * @return
     */
    String get();

    /**
     * 初始化+操作原子化
     * @param initStr
     * @return
     */
    String uniform(String initStr);
}
