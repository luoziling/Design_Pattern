package priv.wzb.datastructure.algorithm.kmp;

import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2019/11/4 22:11
 * @description:
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = kmpNext(str2);
        System.out.println("next=" + Arrays.toString(next));
        int index = kmpSearch(str1, str2, next);
        System.out.println("index = " + index);
    }

    // 写出kmp搜索算法

    /**
     *
     * @param str1 源字符串
     * @param str2 字串
     * @param next 字串的部分匹配表
     * @return 如果-1没匹配否则返回第一个匹配的位置
     */
    public static int kmpSearch(String str1,String str2,int[] next){
        // 遍历
        for (int i = 0,j=0; i < str1.length(); i++) {
            // 需要处理str1.charAt(i) != str2.charAt(j)
            // kmp算法核心
            while (j > 0&& str1.charAt(i) != str2.charAt(j)){
                j = next[j-1];
            }

            if (str1.charAt(i) == str2.charAt(j)){
                j++;
            }
            if (j== str2.length()){
                return i-j+1;
            }
        }
        return -1;
    }

    // 获取到字符串(字串)的部分匹配值
    public static int[] kmpNext(String dest){
        // 创建一个next 数组保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0; //如果字符串长度是1部分匹配值就是0

        // i代表找寻正确的后缀匹配，也就是要跳过第0个字符从第一个字符开始查找
        //
        // j 代表找寻正确的前缀匹配，也就是从第0个字符开始找到length-1
        // 因为正确的前缀匹配总是从第一个字符开始的，所以要寻找部分匹配值必须使得
        // 正确后缀匹配中存在于正确前缀匹配第一个字符相等
        // ！！！j也代表了正确前缀匹配与正确后缀匹配的相同字符串的最大长度也就是部分匹配值
        // 在这个构建部分匹配表的过程中其实也用到了KMP算法
        // 也用到了动态规划法
        // 后一次的匹配结果建立在前一次的基础上
        // i从1开始是因为默认如果要构建部分匹配表的字符串的子串如果只有一个字符
        // 例如ABCDABD的部分匹配表的第一个子串就是“A”只有一个字符就代表部分匹配值为0
        // 只有子串多于一个字符（两个或两个以上字符）才存在部分匹配值大于零
        // 正确前缀匹配与正确后缀匹配相等
        // 例如“AA”，正确前缀匹配为“A”正确后缀匹配为“A”此时的部分匹配值为1

        //todo:分析ij到底代表了什么（重要！！
        

        for (int i = 1,j=0; i < dest.length(); i++) {
            // 当dest.charAt(i) != dest.charAt(j),我们需要从next[j-1]获取新的j
            // 直到我们发现有dest.charAt(i) == dest.charAt(j)才退出
            // 这是kmp算法的核心点
            // 通过找寻正确前缀匹配与正确后缀匹配
            // 可以发现一些规律
            // 正确前缀匹配的子串肯定是以第j个字符开始的
            // 要在正确后缀匹配中查找与正确前缀匹配相同的值肯定得找到第一个字符相同
            // j>0代表了匹配的字符串的子串长度必须大于2

            // 这边如果找不到相同的会回溯向前匹配
            //
            while (j>0 && dest.charAt(i) != dest.charAt(j)){
                // 如果不匹配了，则利用前一次的结论进行回溯验证前一个字符是否匹配
                j = next[j-1];
            }

            // 匹配是否存在相同的正确前缀匹配与正确后缀匹配
            // 如果找到了正确前缀匹配与正确后缀匹配的第一个字符相同
            // 当dest.charAt(i) == dest.charAt(j)满足时，部分匹配值就是+1
            // 在第一次匹配中只有字符串的第一个与第二个字符相同才能形成形同的子串
            // （此时进行前缀后缀匹配的字符串只有两个字符）
            // 例如ABCDABD 此时是对AB进行匹配
            // 正确前缀为A 正确后缀为B 不想等则部分匹配值为0

            // 其实是在这边进行一开是匹配的，因为j的初始值为0，根据前缀匹配与后缀匹配相等的前提就是
            // 后缀匹配的第一个值必须等于前缀匹配的第一个值
            //
            if (dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;

        }
        return next;
    }
}
