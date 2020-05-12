package priv.wzb.leet_code.math.roman_tol_integer_13;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2020/2/25 0:27
 * @description: 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "III"
 * 输出: 3
 * 示例 2:
 * <p>
 * 输入: "IV"
 * 输出: 4
 * 示例 3:
 * <p>
 * 输入: "IX"
 * 输出: 9
 * 示例 4:
 * <p>
 * 输入: "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/roman-to-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int romanToInt(String s) {
        // 可以代表数值的罗马字符
        char[] roman = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        // 罗马字符代表的数字
        int[] num = {1, 5, 10, 50, 100, 500, 1000};
        // 使用map映射取代上述
        Map<Character,Integer> romanMap = new HashMap<>();
        romanMap.put('I',1);
        romanMap.put('V',5);
        romanMap.put('X',10);
        romanMap.put('L',50);
        romanMap.put('C',100);
        romanMap.put('D',500);
        romanMap.put('M',1000);
        // 保存结果
        int res = 0;
//        // 中间取到的字符
//        char c;
//        int index;
//        for (int i = 0; i < s.length(); i++) {
//            c = s.charAt(i);
//            for (int j = 0; j < roman.length; j++) {
//                if (roman[j] == c) {
//                    index = j;
//                }
//            }
//        }
        // 初始化为保存第一个
        char c = s.charAt(0);
        // 初始化result
        res = romanMap.get(c);
        boolean flag = true;
        // 这里从第一个开始取避免最后越界
        for (int i = 1; i < s.length(); i++) {
            // 判断
            // 后一个大于前一个
            if (romanMap.get(s.charAt(i))>romanMap.get(c)){
                // 如果后一个比前一个大那么就是减法
                res+=romanMap.get(s.charAt(i));
                if (flag){
                    // 只有第一次需要减双倍的
                    res-=romanMap.get(c)*2;
                    flag = false;
                }else {
                    res-=romanMap.get(c);
                }

                // 多越过一个
                i++;
                if (i>=s.length()){
                    break;
                }
            }else {
                //后一个小于或等于前一个
                res+=romanMap.get(s.charAt(i));
            }
            // 重置c
            c = s.charAt(i);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().romanToInt("LVIII"));
//        System.out.println(new Solution().romanToInt("MCMXCIV"));
    }
}
