package priv.wzb.multi_threaded_high_concurrent.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/6/1 13:49
 * @description:
 */
public class T03_SynchronizedList {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        List<String> strsSync = Collections.synchronizedList(strs);

    }
}
