package priv.wzb.leet_code.hashmap_and_string.string_conversion_integer_08;

/**
 * @author Satsuki
 * @time 2019/12/29 23:21
 * @description:
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 *
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 *
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 *
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 *
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 *
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 *
 * 说明：
 *
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 *
 * 示例 1:
 *
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 *
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 *
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 *
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 *      因此无法执行有效的转换。
 * 示例 5:
 *
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 *      因此返回 INT_MIN (−231) 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/string-to-integer-atoi
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * atoi的实现
 * 分为三个流程
 * 1.跳过空字符
 * 2.确定正负号
 * 3.找出数字部分
 * 大数问题，异常处理
 */
public class Solution {
    public int myAtoi(String str) {
        // 去空格
//        str.trim();
        // 判空，对于形参的规格判定是否符合
        if (str == null || str.length()==0){
            return 0;
        }

        //1.跳过空字符，这里使用str.trim()也可以去掉字符串两端的空字符
        int i = 0;
        while (i<str.length() && str.charAt(i)==' '){
            i++;
        }
        // 此时，i指向第一个非空字符或者i越界
        if (i == str.length()){
            return 0;
        }
        // 2.判断数字的符号
        int flag=1;
        char ch = str.charAt(i);
        // +号代表正整数
//        if (ch=='+'){
//            // 置1
//            flag=1;
//            // 跳过符号位
//            i++;
//        }
//        if (ch>='0' && ch<='9'){
//            flag = 1;
//        }
//        if (ch=='-'){
//            flag = -1;
//            i++;
//        }
        if (ch=='+'){
            // 跳过符号位
            i++;
        }else if (ch=='-'){
            flag=-1;
            i++;
        }
        // 3.找出数字副本
        int res = 0;
        for (; i < str.length(); i++) {
            ch = str.charAt(i);
            // 非数字
            if (ch<'0' || ch>'9'){
                // 跳出循环
                break;
            }

            // 溢出判断
            // 判断正溢出
            // 这里的判断加了多余项
            // 题意中的32位郑海代表4个字节正好是int类型的极限值
            // 对于i+1<str.length() && str.charAt(i+1)>='0' && str.charAt(i+1) <='9'
            // 可以不用判断
//        if (flag>0 && i+1<str.length() && str.charAt(i+1)>='0' && str.charAt(i+1) <='9' && res>Integer.MAX_VALUE){
//            return Integer.MAX_VALUE;
//        }
            // 这一次的结果已经超出了最大值
            if (flag>0 && res>Integer.MAX_VALUE/10){
                return Integer.MAX_VALUE;
            }
            // 在下一次附加后可能会越界的情况
            if (flag>0 && res==Integer.MAX_VALUE/10 && ch-'0'>Integer.MAX_VALUE % 10){
                return Integer.MAX_VALUE;
            }

            // 负溢出
            // 这一次的结果已经超出了32位int的最小值
            if (flag<0 && -res<Integer.MIN_VALUE/10){
                return Integer.MIN_VALUE;
            }
            // 在下一次附加后可能会越界的情况
            if (flag<0 && -res==Integer.MIN_VALUE/10 && -(ch-'0')<Integer.MIN_VALUE % 10){
                return Integer.MIN_VALUE;
            }

            // 数字整合
            res = res*10+ch-'0';
        }



        // 加入正负
        return res*flag;


    }

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE%10);
        System.out.println(Math.abs(Integer.MIN_VALUE%10));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(new Solution().myAtoi(" -42"));
    }
}
