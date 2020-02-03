package priv.wzb.leet_code.longest_palindromic_Substring_05;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/7/22 16:44
 * @description:
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public String longestPalindrome(String s) {
        String subString = null;
        String maxSubString = null;
        //Map用于记录每一个字符以及出现的次数
        Map<Character,Integer> sMap = new HashMap<>();
        //遍历字符串将所有字符都存入Map
        for (int i = 0; i < s.length(); i++) {
            //如果存在该字符可能这两个相同字符存在回文串可以进行判断跳出/返回
            if(sMap.containsKey(s.charAt(i))){
                //获取子串
                subString = s.substring(sMap.get(s.charAt(i)),i);
                if(isPalindromic(subString)){
                    if(maxSubString == null){
                        maxSubString = subString;
                    }else{
                        if(subString.length()>maxSubString.length()){
                            maxSubString = subString;
                        }
                    }
                }

            }
            //可能需要在图中存储字符串的位置
            //如果出现相同的则更新位置
            sMap.put(s.charAt(i),i);
            //如果在map中存在则加一
//            if (sMap.containsKey(s.charAt(i))){
//                sMap.put(s.charAt(i),sMap.get(s.charAt(i))+1);
//            }else {
//                //否则作为新字符串加入map中
//                sMap.put(s.charAt(i),1);
//            }
        }
        return maxSubString;
    }

    public static boolean isPalindromic(String s){
        boolean flag = true;
        int median = s.length()/2;
        if (s.length()/2==0){
            //偶数
            for (int i = 0; i < median; i++) {
                if(s.charAt(median-i-1)!=s.charAt(median+i)){
                    flag = false;
                }
            }
        }else if (s.length()/2==1){
            //奇数个字符
            for (int i = 0; i < median; i++) {
                if(s.charAt(median-i-1)!=s.charAt(median+i+1)){
                    flag = false;
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        String s = "babad";
//        String s = "bb";

//        isPalindromic("aaa");
        System.out.println(new Solution().longestPalindrome(s));
    }
}
