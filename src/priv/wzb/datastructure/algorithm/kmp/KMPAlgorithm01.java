package priv.wzb.datastructure.algorithm.kmp;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/7 0:21
 * @description:
 */
public class KMPAlgorithm01 {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

        int[] next = getNext(str2);
        System.out.println("next=" + Arrays.toString(next));
        int index = KMPSearch(str1, str2, next);
        System.out.println("index = " + index);

    }

    /**
     * KMP算法匹配字符串
     * @param origin 原字符串
     * @param pattern 匹配字符串/模式/样式字符串
     * @param next 部分匹配表
     * @return 如果匹配到返回第一个相等字符的下标，匹配不到返回-1
     */
    public static int KMPSearch(String origin,String pattern,int[] next){
        int i,j;
        int n = origin.length();
        int skip;
        //循环匹配
        for (i = 0,j=0; i < n; i++) {
            System.out.println("i:" + i);
            // 不匹配跳过一定字符
            if (j>0&& origin.charAt(i)!=pattern.charAt(j)){
                // 跳过的字符个数：
                skip = next[j-1];
                skip = j-skip;
//                i = i - next[j] + 1;
                // 跳过
                i = i-j+skip;
                // j一定需要重置
//                System.out.println("j:" + j);
                j = 0;
            }

//            while (j>0&&origin.charAt(i)!=pattern.charAt(j)){
//                j = next[j-1];
//            }

            if (origin.charAt(i) == pattern.charAt(j)){
                j++;
            }

            // 如果匹配到返回第一个相等字符的下标
            if (j==pattern.length()){
                return i-j+1;
            }
        }
        // 匹配不到
        return -1;
    }

    /**
     * 获取部分匹配表
     * @param pattern 带获取的样式子串
     * @return 部分匹配表
     */
    public static int[] getNext(String pattern){
        int[] next = new int[pattern.length()];

        // 特例情况，当样式串的子串只有一个字符此时部分匹配值必定是0
        // 0不仅代表了下标还代表了样式串子串的长度
        next[0] = 0;

        //遍历子串建立部分匹配表
        for (int i = 1,j=0; i < pattern.length(); i++) {
            // 截断，未找到相等的前缀和后缀
            while (j>0 && pattern.charAt(i) != pattern.charAt(j)){
                j = next[j-1];
            }

            // 当有相等的前缀与后缀
            if (pattern.charAt(i) == pattern.charAt(j)){
                j++;
            }
            next[i] = j;
        }

        return next;
    }
}
