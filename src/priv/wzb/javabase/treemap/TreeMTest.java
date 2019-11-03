package priv.wzb.javabase.treemap;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Satsuki
 * @time 2019/9/4 13:43
 * @description:
 */
public class TreeMTest {
    public static void main(String[] args) {
        Map<Employee,String> treeMap = new TreeMap<>();
        treeMap.put(new Employee(100,"a",40000),"branch0");
        treeMap.put(new Employee(300,"b",4000),"branch1");
        treeMap.put(new Employee(200,"c",5000),"branch2");

        //按key递增方式排序
        for (Employee key:treeMap.keySet()){
            System.out.println(key + "---" + treeMap.get(key));
        }
    }
}
