package priv.wzb.javabase.manualhashset;

import java.util.HashMap;

/**
 * @author Satsuki
 * @time 2019/9/4 13:59
 * @description:
 */
public class WzbHashSet {
    HashMap map;

    private static final Object PRESENT = new Object();

    public WzbHashSet() {
        map = new HashMap();
    }

    public int size(){
        return map.size();
    }

    public void add(Object o){
        map.put(o,PRESENT);
    }
}
