package priv.wzb.leet_code.hashmap_and_string.offer_50;

import java.util.HashMap;

/**
 * Solution
 *
 * @author yuzuki
 * @date 2021/8/15 22:57
 * @description:
 * @since 1.0.0
 */
public class Solution {
    public static char firstUniqChar(String s) {
        // TreeMap的有序是指key的有序，key应该重写了compare等方法
        HashMap<Character,Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            Character key = c;
            if (!map.containsKey(key)){
                map.put(key,1);
            }else {
                map.put(key,map.get(key)+1);
            }
        }
        for (char aChar : chars) {
            if (map.get(aChar) == 1){
                return aChar;
            }
        }
        return ' ';

    }

    public static void main(String[] args) {
        System.out.println("firstUniqChar(\"leetcode\") = " + firstUniqChar("leetcode"));
    }
}
