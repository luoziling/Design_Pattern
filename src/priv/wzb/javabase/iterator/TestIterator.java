package priv.wzb.javabase.iterator;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/9/4 14:09
 * @description:
 */
public class TestIterator {
    public static void main(String[] args) {
        testIteratorMap();
    }

    public static void testIteratorList(){
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        Iterator<String> iterator;

        for(iterator=list.iterator();iterator.hasNext();){
            String temp = iterator.next();
            System.out.println(temp);
        }
    }

    public static void testIteratorSet(){
        Set<String> set = new HashSet<>();
        set.add("aa");
        set.add("bb");
        set.add("cc");
        Iterator<String> iterator;

        for(iterator=set.iterator();iterator.hasNext();){
            String temp = iterator.next();
            System.out.println(temp);
        }
    }

    public static void testIteratorMap(){
        Map<Integer,String> map1 = new HashMap<>();
        map1.put(100,"aa");
        map1.put(200,"bb");
        map1.put(300,"cc");

//        Set<Map.Entry<Integer, String>> entries = map1.entrySet();
//        for (Iterator<Map.Entry<Integer,String>>iter = entries.iterator();iter.hasNext();){
//            Map.Entry<Integer, String> next = iter.next();
//            System.out.println(next);
//        }

        Set<Integer> set = map1.keySet();
        for(Integer i:set){
            System.out.println(map1.get(i));
        }
//        for(Iterator<Integer>iter = set.iterator();iter.hasNext();){
//
//        }
    }


}
