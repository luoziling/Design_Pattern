package priv.wzb.javabase.collection;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/9/4 15:59
 * @description:
 */
public class TestStoreData {
    public static void main(String[] args) {

        Map<String,Object> row1 = new HashMap<>();
        row1.put("id","1001");
        row1.put("name","a1");
        row1.put("salary","20000");
        row1.put("入职日期","2018.5.5");

        Map<String,Object> row2 = new HashMap<>();
        row2.put("id","1002");
        row2.put("name","a2");
        row2.put("salary","2000");
        row2.put("入职日期","2018.5.5");

        Map<String,Object> row3 = new HashMap<>();
        row3.put("id","1003");
        row3.put("name","a3");
        row3.put("salary","200");
        row3.put("入职日期","2018.5.5");

        List<Map<String,Object>> table1 = new ArrayList<>();

        table1.add(row1);
        table1.add(row2);
        table1.add(row3);

        System.out.println(table1.size());

        for(Map<String,Object>row:table1){
            System.out.println(row);
            Set<String> set = row.keySet();
            for(String key:set){
                System.out.println(key+ ":" + row.get(key));
            }
        }
    }
}
