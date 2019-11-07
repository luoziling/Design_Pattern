package priv.wzb.datastructure.algorithm.kmp;

/**
 * @author Satsuki
 * @time 2019/11/4 21:52
 * @description:
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        // 尚a硅b谷c你d好e
        String str1 = "bbc abcdab abcdabcdabde";
        String str2 = "abcdabd";
        long now = System.currentTimeMillis();
        int inddex = violenceMatch(str1,str2);
        long after = System.currentTimeMillis();
        System.out.println("耗时：" + (after-now));
        System.out.println("index = " + inddex);
    }

    //暴力匹配算法实现
    public static int violenceMatch(String str1,String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int i = 0; // i索引指向s1
        int j = 0; // j索引指向s2
        // 保证匹配时不越界
        while (i<s1Len&&j<s2Len){
            if (s1[i] == s2[j]){
                //匹配成功
                i++;
                j++;
            }else {
                // 没匹配成功
                i=i-(j-1);
                j=0;
            }
        }

        // 判断是否匹配成功
        if (j == s2Len){
            return i-j;
        }else {
            return -1;
        }
    }
}
