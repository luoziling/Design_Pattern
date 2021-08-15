package priv.wzb.leet_code.dynamic_programming.offer_49;

/**
 * T1Test
 *
 * @author yuzuki
 * @date 2021/8/13 0:02
 * @description:
 * @since 1.0.0
 */
public class T1Test {
    public static int characterReplacement(String s, int k) {
        // 滑动窗口
        int left = 0,right = 0;
        int max = 0;
        char[] chars = s.toCharArray();
        // 记录最大相同
        int maxSame = 0;
        // 记录每种出现的次数
        int[] nums = new int[26];

        for (int i = 0; i < chars.length; i++) {
            // 不断统计字符及其个数
            nums[chars[i]-'A']++;
            // 统计最大相同数
            maxSame = Math.max(maxSame,nums[chars[i]-'A']);
            // 若区间内数字-最大相同数任然小于限制则left++来消除
            if (right-left+1-maxSame > k){
                // 对应自减
                nums[chars[left]-'A']--;
                left++;
            }

            // 不断右移
            right++;
        }
        return right-left;
    }

    public static void main(String[] args) {
        String s = "AABABBA";
        int k = 1;
        System.out.println("characterReplacement(s,k) = " + characterReplacement(s, k));
    }
}
