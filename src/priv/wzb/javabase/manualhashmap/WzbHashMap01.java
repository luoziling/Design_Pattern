package priv.wzb.javabase.manualhashmap;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/9/2 16:53
 * @description:
 */
public class WzbHashMap01 {

    Node2[] table;//位桶数组 bucket array
    int size; //存放键值对个数

    public WzbHashMap01() {
        table = new Node2[16]; //长度一般为2的整数幂


    }

    public Object get(Object key){
        int hash = myHash(key.hashCode(),table.length);
        Object value = null;

        if(table[hash]!=null){
            Node2 temp = table[hash];
            while (temp!=null){
                if (temp.key.equals(key)){
                    value = temp.value;
                    break;
                }else {
                    temp = temp.next;
                }
            }
        }

        return value;
    }

    public void put(Object key,Object value){
        Node2 newNode = new Node2();
        newNode.hash = myHash(key.hashCode(),table.length);
        newNode.key = key;
        newNode.value = value;
        newNode.next = null;

        Node2 temp = table[newNode.hash];

        //正在遍历的最后一个元素
        Node2 iterLast = null;
        boolean keyRepeat = false;
        if(temp == null){
            //元素为空将新节点放入
            table[newNode.hash] = newNode;
            size++;

        }else {
            //元素不为空则遍历链表
            while (temp!=null){
                //key重复则覆盖
                //不重复就找下一个
                if(temp.key.equals(key)){
                    System.out.println("key重复");
                    keyRepeat = true;
                    temp.value = value;
                    break;
                }else {
                    iterLast = temp;
                    temp = temp.next;
                }
            }
            if (!keyRepeat){
                iterLast.next = newNode;
                size++;
            }



        }


    }

    public int myHash(int v,int length){
        //位运算效率高
        System.out.println("hash in myHash"+(v&(length-1)));
        //取模运算效率低
        System.out.println("hash in myHash"+(v%(length-1)));
        return v&(length-1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < table.length; i++) {
            Node2 temp = table[i];
            while (temp!=null){
                sb.append(temp.key + ":" + temp.value + ",");
                temp = temp.next;
            }

        }
        sb.setCharAt(sb.length()-1,'}');

        return sb.toString();
//        return "WzbHashMap01{" +
//                "table=" + Arrays.toString(table) +
//                ", size=" + size +
//                '}';
    }

    public static void main(String[] args) {
        WzbHashMap01 m = new WzbHashMap01();
        m.put(10,"a");
        m.put(20,"b");
        m.put(30,"c");
        m.put(20,"d");
        m.put(53,"d");
        m.put(69,"d");
        m.put(85,"d8");

        System.out.println(m.toString());
        System.out.println(m.get(85));


        new Object();
    }
}