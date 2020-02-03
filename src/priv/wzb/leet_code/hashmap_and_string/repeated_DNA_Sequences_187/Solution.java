package priv.wzb.leet_code.hashmap_and_string.repeated_DNA_Sequences_187;

import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/28 20:15
 * @description:.
 *
 * 只需要遍历一遍数组然后通过映射将长度10的子串与出现次数加入map即可，有重复添加次数无重复新建映射
 */
public class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        // 映射用于在遍历时保存大于十的子串出现次数
        Map<String,Integer> map = new HashMap<>();
        // 子串
        String subString;
        // 保存结果
        List<String> stringList = new ArrayList<>();
        // 注意subString是[)所以最后一位一定要注意取到+1
        for (int i = 10; i <= s.length(); i++) {
            subString = s.substring(i-10,i);
            if (map.containsKey(subString)){
                // 出现过，次数+1
                map.put(subString,map.get(subString)+1);
            }else {
                // 未保存过，添加新映射
                map.put(subString,1);
            }
        }
        // 遍历添加出现次数大于1的
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            if (entry.getValue()>1){
                stringList.add(entry.getKey());
            }
        }

        return stringList;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findRepeatedDnaSequences("AAAAAAAAAAA"));
    }
}
