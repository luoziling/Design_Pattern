package priv.wzb.leet_code.hashmap_and_string.zigzag_conversion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/12/15 23:23
 * @description:
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 *
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 *
 * string convert(string s, int numRows);
 * 示例 1:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 *
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;

        // rows中保存的是每一行的字符，z字形蜿蜒字符
        List<StringBuilder> rows = new ArrayList<>();


        for (int i = 0; i < Math.min(numRows,s.length()); i++) {
            rows.add(new StringBuilder());
        }

        // 当前行
        int curRow = 0;

        // 用于标记是否方向要发生改变，只有当我们向上移动到最上面的行或者向下移动到最下面的行才需要改变
        boolean goingDown = false;

        // 遍历
        for (char c: s.toCharArray()){
            rows.get(curRow).append(c);
            // 向上移动到最上面的行或者向下移动到最下面的行
            if (curRow==0 || curRow==numRows-1) goingDown = !goingDown;
            curRow += goingDown ? 1:-1;
        }
        StringBuilder ret = new StringBuilder();
        for(StringBuilder row:rows){
            ret.append(row);
        }
        return ret.toString();
    }
}
