package priv.wzb.leet_code.array.minimum_increment_to_make_array_unique_945;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Satsuki
 * @time 2020/3/22 15:14
 * @description:
 * 给定整数数组 A，每次 move 操作将会选择任意 A[i]，并将其递增 1。
 *
 * 返回使 A 中的每个值都是唯一的最少操作次数。
 *
 * 示例 1:
 *
 * 输入：[1,2,2]
 * 输出：1
 * 解释：经过一次 move 操作，数组将变为 [1, 2, 3]。
 * 示例 2:
 *
 * 输入：[3,2,1,2,1,7]
 * 输出：6
 * 解释：经过 6 次 move 操作，数组将变为 [3, 4, 1, 2, 5, 7]。
 * 可以看出 5 次或 5 次以下的 move 操作是不能让数组的每个值唯一的。
 * 提示：
 *
 * 0 <= A.length <= 40000
 * 0 <= A[i] < 40000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-increment-to-make-array-unique
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution2 {
    // 把这个看作地址映射
    // 就是要解决地址冲突问题
    // 把一些数据放到不冲突的地址内
    // pos代表了内存
    // 为了防止40000个40000冲突
    // 为此可以将地址扩充至80000个
    // 解决pos中的地址冲突问题
    // 数组下标代表了地址空间，内容代表了其中是否存放过数，使用路径压缩后代表了探测到了哪个位置
    int[] pos = new int[80000];
    public int minIncrementForUnique(int[] A) {
        if (A.length<2){
            return 0;
        }
        int ans=0;
        // 使用线性探测法

        Arrays.fill(pos,-1); // -1表示空
        // 遍历每个数字a对其寻地址得到位置b，b比a的增量就是操作数
        for (int a :
                A) {
            int b = findPos(a);
            ans+=b-a;
        }
        return ans;

    }

    // 线性探测寻址（含路径压缩）
    private int findPos(int a){
        int b = pos[a];
        // 如果a对应的位置pos[a]是空位，直接放入
        if (b==-1){
            pos[a]=a;
            return a;
        }
        // 否则向后寻址
        // 因为pos[a]中标记了上次寻址得到的空位，因此从pos[a]+1开始寻址就行了（不需要从a+1开始）
        b=findPos(b+1);
        pos[a]=b;// 寻址后的新空位要重新赋值给pos[a] 路径压缩
        return b;
    }
}
