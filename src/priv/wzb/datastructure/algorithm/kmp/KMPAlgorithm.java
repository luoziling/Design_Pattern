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
        long now = System.currentTimeMillis();
        int index = kmpSearch(str1, str2, next);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end-now));
        System.out.println("index = " + index);

        // 测试next数组（部分匹配表）
//        String str3 = "abababca";
//        int[] next = kmpNext(str3);
//        System.out.println("next:" + Arrays.toString(next));
    }

    // 写出kmp搜索算法
    /**
     * KMP算法匹配字符串
     * @param origin 原字符串
     * @param pattern 匹配字符串/模式/样式字符串
     * @param next 部分匹配表
     * @return 如果匹配到返回第一个相等字符的下标，匹配不到返回-1
     */
    public static int kmpSearch(String origin,String pattern,int[] next){
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

//    /**
//     *
//     * @param str1 源字符串
//     * @param str2 字串
//     * @param next 字串的部分匹配表
//     * @return 如果-1没匹配否则返回第一个匹配的位置
//     * 这里根本没有实现动态规划KMP
//     * 甚至根本没有产生回溯
//     *
//     */
//    public static int kmpSearch(String str1,String str2,int[] next){
//        // 遍历
//        for (int i = 0,j=0; i < str1.length(); i++) {
//            System.out.println("i:" + i);
//            // 需要处理str1.charAt(i) != str2.charAt(j)
//            // kmp算法核心
//            // 当不相等的时候查部分匹配表
//            // 查询在表中已经匹配字符所代表的部分匹配值
//            // 这里很巧妙的用了while循环而不是用公式partial_match_length - table[partial_match_length - 1]来判断跳过的次数
//            // 通过在建立部分匹配表时候的发现，可以看出部分匹配表中的数值其实是有关联的
//            // 到0之前的数值是递增的，要跳过多少个字符就是就等于在部分匹配表中前移多少次
//            // 由此此处这样写
//            // j一定需要重置j的重置
//
//            while (j > 0&& str1.charAt(i) != str2.charAt(j)){
////                System.out.println("j:" + j);
//                j = next[j-1];
//            }
//
//            // 如果字符匹配则后移下标
//            if (str1.charAt(i) == str2.charAt(j)){
//                j++;
//            }
//
//            // 当子串全部找到即可返回子串在原字符串中第一次出现的位置
//            if (j== str2.length()){
//                return i-j+1;
//            }
//        }
//        // 未找到返回-1
//        return -1;
//    }

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
        

        // i代表了不停后移的next数组的index（j） j代表了k
        // i代表的index指向的字符也代表了正确的后缀子串必定会拥有的一个字符

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

            // 假设我们要建立部分匹配表的字符串是“abababca”
            // 当Pattern子串“abababc”通过这句话(dest.charAt(i) != dest.charAt(j))(a!=c)的判断可以得知不相等得到不会进行此时将j = next[j-1](其实这个新欢的意义就在于因为Pattern子串肯定会包含字符“c”而dest.charAt(i)一开始并没有找到这个字符那就回溯的向前找（会回溯是因为可以观察部分匹配表发现其实部分匹配值是有规律是是一个递增的过程，这个部分匹配值也可以代表一种下标（此时后缀字符串必定包含字符c，字符c的下标在6，此时下标6的字符与下标4的字符相比并不相等，发现了么，下标4，下标4不就是j的值么，在不相等的情况下j会向前回溯j = next[j-1];可以根据部分匹配表的规律发现其实就是将匹配的下标迁移了，不断的前移进行回溯性的匹配直至到第0个字符还未找到相等的字符也就是说在前缀中没有能有后缀想匹配的子串此时这里的结果j也就变成了0））)(此时j=0，next[6] = 0,可以看到在这一次查找部分匹配值的时候其实用上了上一次的结果
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

            // 在建立部分匹配表的过程中
            // 在寻找正确前缀和正确后缀的过程中
            // 样式子串(Pattern)也可以说是模式串是在不停的变化的
            // 这里就使用了动态规划算法的特性：后一次的结果是建立在前一次的结果基础之上的
            // next[i]也就代表了要寻找部分匹配值的样式子串（从下标0开始到i[],是包含0和i的)
            // 例如abababca的部分匹配表是next:[0, 0, 1, 2, 3, 4, 0, 1]
            // 我们来分析以下这张表
            // 当Pattern子串只有一个字符的时候毫无疑问就是0
            // 当Pattern有两个字符“ab”通过这句话(dest.charAt(i) == dest.charAt(j))的判断可以得知不相等得到0
            // 当Pattern子串“aba”通过这句话(dest.charAt(i) == dest.charAt(j))(a==a)的判断可以得知相等得到j++(1)(此时next[2] = 1
            // 当Pattern子串“abab”通过这句话(dest.charAt(i) == dest.charAt(j))(b==b)的判断可以得知相等得到j++(2)(此时next[3] = 2,可以看到在这一次查找部分匹配值的时候其实用上了上一次的结果
            // 当Pattern子串“ababa”通过这句话(dest.charAt(i) == dest.charAt(j))(a==a)的判断可以得知相等得到j++(3)(此时next[4] = 3,可以看到在这一次查找部分匹配值的时候其实用上了上一次的结果
            // 当Pattern子串“ababab”通过这句话(dest.charAt(i) == dest.charAt(j))(b==b)的判断可以得知相等得到j++(4)(此时next[5] = 4,可以看到在这一次查找部分匹配值的时候其实用上了上一次的结果
            // 当Pattern子串“abababc”通过这句话(dest.charAt(i) == dest.charAt(j))(a!=c)的判断可以得知不相等得到不会进行j++,其实在上面执行的时候就被拦截了下来（接下去我们转到上面的代码继续分析）(此时next[5] = 4,可以看到在这一次查找部分匹配值的时候其实用上了上一次的结果

            //根据对上面循环的描述可以得出这个等式不会成立，也就是说next[6] = 0
            // 接下去又仿佛回到了最初
            // 当Pattern子串“abababca”通过这句话(dest.charAt(i) == dest.charAt(j))(a==a)的判断可以得知相等得到j++(1)(此时next[7] = 1
            // 至此，分析完毕
            if (dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;

        }
        return next;
    }
}
