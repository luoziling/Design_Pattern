package priv.wzb.javabase.manualhashmap;

/**
 * @author Satsuki
 * @time 2019/9/2 16:54
 * @description:
 * 节点应用于WzbHashMap中
 */
public class Node3<K,V> {

    int hash;
    K key;
    V value;
    Node3 next;
}
