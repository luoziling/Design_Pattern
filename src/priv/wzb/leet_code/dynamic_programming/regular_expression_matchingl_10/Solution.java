package priv.wzb.leet_code.dynamic_programming.regular_expression_matchingl_10;

/**
 * @author Satsuki
 * @time 2020/1/18 19:29
 * @description:
 */
public class Solution {

    /**
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     *
     * @param s 可能为空，且只包含从 a-z 的小写字母。
     * @param p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
     * @return 字符串s 是否根据pattern n完成匹配
     */
    public boolean isMatch(String s, String p) {
        // 判断s或者p是否存在
        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        char[] ps = p.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            // 哪一边先完成匹配但另一边未完成匹配则表示匹配失败
//            if ((i>=chars.length && i<ps.length)|| (i>=ps.length && i<chars.length)){
//                return false;
//            }
//            if (ps[i] == chars[i] || )
//        }
        // i和j作为下标遍历chars和ps两个数组
        int i,j;
        i=j=0;
//        while ((i>=chars.length && j<ps.length)|| (j>=ps.length && i<chars.length)){
//
//        }
        // 保存*号匹配的字符
        char matc='1';
        // 是否需要*号匹配
        boolean flag= false;
        while (i<chars.length && j<ps.length){
            // 单个字符匹配的情况
            if (ps[j] == chars[i] || ps[j]=='.'){
                i++;
                j++;
            }else if (ps[j]=='*'){
                // *号匹配
//                if (flag==false){
//                    matc = ps[j-1];
//                }

                matc = ps[j-1];
                // 当前已匹配
                j++;
                while (i<chars.length&&chars[i] == matc ){
                    i++;
                }
                // .*的情况
                if (ps[j-1]=='.'){
                    if (j+1==ps.length){
                        return true;
                    }else if(j+1<ps.length){
                        // 未匹配到最后一个但是存在.*
                        return false;
                    }
                }
                // 通过*匹配到了最后
                if (i==chars.length && j==ps.length){
                    return true;
                }
            }else {
                // 不匹配
                break;
            }
        }
        return false;

    }


    public static void main(String[] args) {
//        String s = "mississippi";
//        System.out.println(new Solution().isMatch(s,"\"mis*is*p*.\""));


        String s = "aa";
        System.out.println(new Solution().isMatch(s,"a*"));
//        System.out.println(s.matches("mis*is*p*."));
//        System.out.println(s.matches("mis*is*ip*."));
////        System.out.println(s.matches("mis*is*p*"));
//        String s1 = "";
//        System.out.println(s.matches(""));
    }
}
