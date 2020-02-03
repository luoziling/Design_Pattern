package priv.wzb.leet_code.dynamic_programming.regular_expression_matchingl_10;

/**
 * @author Satsuki
 * @time 2020/1/19 17:45
 * @description:
 * 通过回溯法求解
 * 分为两种情况带*的匹配与不带*的匹配
 *
 */
public class Solution1 {
    public boolean isMatch(String text, String pattern) {
        // 都为空的时候才判断正确
        // 如果只有pattern未空 text不是空就是false
        if (pattern.isEmpty()){
            return text.isEmpty();
        }
        // 判断第一个字符是否匹配
        // 当text不为空且text的第一个字符与pattern的第一个字符相等
        // 或者pattern的第一个字符为'.'
        // 就可以匹配
        boolean firstMatch = (!text.isEmpty() && (pattern.charAt(0)==text.charAt(0)||pattern.charAt(0)=='.'));
        if (pattern.length()>=2 && pattern.charAt(1) == '*'){
            // 回溯
            // 当pattern长度大于等于2且pattern的第二个字符为*
            // 判断
            // substring中只有一个数值x就取原字符串的子字符串从第x个开始取到最后（包括x）从0开始计数
            // 可能text是abbbb...的类型匹配
            // 或者第一个字符匹配继续匹配下一个字符（text的字符指针后移继续判断匹配
            return (isMatch(text,pattern.substring(2))||(firstMatch&& isMatch(text.substring(1),pattern)));
        }else {
            // 当长度为1或者第二个字符不为*的情况
            // 如果长度唯一就返回这一个字符是否匹配，如果长度大于1且pattern的匹配下一个字符不是*就都后移匹配
            return firstMatch&& isMatch(text.substring(1),pattern.substring(1));
        }
    }

    public static void main(String[] args) {
        String s = "";
        System.out.println(s.isEmpty());
    }
}
