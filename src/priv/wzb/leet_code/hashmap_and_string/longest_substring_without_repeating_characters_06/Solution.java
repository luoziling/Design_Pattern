package priv.wzb.leet_code.hashmap_and_string.longest_substring_without_repeating_characters_06;

/**
 * @author Satsuki
 * @time 2019/7/18 15:18
 * @description:
 */
public class Solution {

    /**
     * 返回最长无重复子字符串长度
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        //长度初始化
        //最大长度
        int maxLength=0;
        //所有出现的非重复子字符串的长度
        int myMax=0;
        //可能出现的边界情况比如""与" "
        if (s.equalsIgnoreCase("")){
            maxLength=0;
//            return maxLength;
        }else if(s.indexOf(" ")>=0){
            maxLength=1;
        }
        //通过StringBuilder不断的连接求解非重复子字符串
        StringBuilder subString = new StringBuilder("");
//        String subString1 = "";
        for (int i = 0; i < s.length(); i++) {
            //每次循环将可能得到的非重复子字符串长度置零
            myMax=0;
//            String a = String.valueOf(s.charAt(i));
//            System.out.println(s.charAt(i));
//            subString = new StringBuilder("a");
//            subString = new StringBuilder(s.charAt(i));

            //初始化子字符串
            subString = new StringBuilder(String.valueOf(s.charAt(i)));
//            subString1 = new String(String.valueOf(s.charAt(i)));
            //子字符串中有第一个字符了
            myMax++;
            //第二层循环从i+1开始向后找直到找到出现相同字符的情况
            for (int j = i+1; j < s.length(); j++) {
//                System.out.println(String.valueOf(s.charAt(j)));
                //判断有没有相同字符的出现
                if (subString.indexOf(String.valueOf(s.charAt(j)))>=0){
                    //找到重复字符串了

                    if(myMax>maxLength){
                        maxLength = myMax;
                        subString = null;
                    }
                    //跳出第二层循环
                    break;
                }else{
                    //否则以第i个字符串为基准的长度++
//                    myMax++;
                    //将该字符添加到字串中
                    subString.append(s.charAt(j));
//                    System.out.println(subString);
                    //
                    myMax = subString.length();
                }
            }
            if(myMax>maxLength){
                //更新最长值
                maxLength = myMax;
            }
        }
        System.out.println(subString.toString());
        return maxLength;
    }

    public static void main(String[] args) {
//        String s = "abcabcbb";
//        String s = "bbbbb";
//        String s = "pwwkew";
//        String s = "";
//        String s = " ";
//        String s = "c";
//        String s = "au";
        String s = "jbpnbwwd";
        System.out.println(new Solution().lengthOfLongestSubstring(s));
    }
}
