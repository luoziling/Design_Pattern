package priv.wzb.leet_code.longest_palindromic_Substring_05;

/**
 * @author Satsuki
 * @time 2019/11/9 19:36
 * @description:
 * 中心扩展解决最长回文子串问题
 * 回文串一定是对称的，我们可以每次循环选择一个中心，进行左右扩展，判断左右字符是否相等即可
 * 由于存在奇偶数的子字符串，我们需要从要给字节开始扩展，或者两个字符中间开始扩展，所以共有n+n-1个中心
 */
public class CenterExpand {
    public String longestPalindrome(String s) {
        if (s== null || s.length()<1) return "";
        int start = 0,end = 0;
        for (int i = 0; i < s.length(); i++) {
            // 奇数个
            int len1 = expandAroundCenter(s,i,i);
            // 偶数个
            int len2 = expandAroundCenter(s,i,i+1);
            int len = Math.max(len1,len2);
            if (len>end-start){
                start = i-(len-1)/2;
                end = i+len/2;
            }
        }

        // end+1是因为sub返回的是【），右边是取不到的
        return s.substring(start,end+1);
    }

    private int expandAroundCenter(String s,int left,int right){
        int L = left,R = right;
        // 左右不越界的前提下从中心扩展的字符相等
        // 在第一遍判断的时候
        // 奇数个数的字符串中心点自己肯定是和自己相等的
        // 偶数个数的中心点右两个，中心点也是相等的
        // 2  4 0
        while (L>=0&&R<s.length()&&s.charAt(L) == s.charAt(R)){
            L--;
            R++;
        }
        return R-L-1;
    }


    public static void main(String[] args) {
        String s = "babad";
//        String s = "bb";

//        isPalindromic("aaa");
        System.out.println(new CenterExpand().longestPalindrome(s));
    }
}
