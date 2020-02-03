package priv.wzb.leet_code.longest_palindromic_Substring_05;

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
 * 思路：
 * 使用堆栈，入栈一半再出来和另一半对比，其实花费的时间还是O（n）
 * 重点在于对于是否是会问数组的判定，如何使用动态规划法
 * 如果判定到一个回文字符串，那么在下一次判定可以判定这个字符串的前后是否加入了相同的字符
 * 加入了就说明是回文字符串，没加入就说明不是
 * 借助滑动窗口来遍历 （队列）
 * 最长公共子串
 */
public class LongestPubSubString {

    public String longestPalindrome(String s) {
        if (s.equals(""))
            return "";
        String origin = s;
        // 字符串倒置
        String reverse = new StringBuilder(s).reverse().toString();
        int length = s.length();
        // 用于保存数据，所有回文子串的长度，行代表了结尾字符的位置
        int[][] arr = new int[length][length];
        // 最长回文子串的长度
        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (origin.charAt(i) == reverse.charAt(j)){
                    if (i==0 || j==0){
                        arr[i][j] = 1;
                    }else {
                        arr[i][j] = arr[i-1][j-1]+1;
                    }
                }
                if (arr[i][j]>maxLen){
                    int beforeRev = length-1-j;
                    // 判定头尾是否相同
                    if (beforeRev + arr[i][j]-1 == i){
                        maxLen = arr[i][j];
                        maxEnd = i; //以i位置结尾的字符
                    }
//                    maxLen = arr[i][j];
//                    maxEnd = i; //以i位置结尾的字符
                }
            }
        }

        return s.substring(maxEnd-maxLen+1,maxEnd+1);

    }



//    public boolean isPalindrome(LinkedList<Character> subList){
//
//    }



    public static void main(String[] args) {
        String s = "babad";
//        String s = "bb";
//        String s = "b";


//        isPalindromic("aaa");
        System.out.println(new LongestPubSubString().longestPalindrome(s));
    }
}
