package priv.wzb.leet_code.qianxin.remove_boxes;

import com.sun.org.apache.regexp.internal.RE;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/11/9 21:25
 * @description:
 * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色。
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k*k 个积分。
 * 当你将所有盒子都去掉之后，求你能获得的最大积分和。
 *
 * 示例 1：
 * 输入:
 *
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * 输出:
 *
 * 23
 * 解释:
 *
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
 * ----> [1, 3, 3, 3, 1] (1*1=1 分)
 * ----> [1, 1] (3*3=9 分)
 * ----> [] (2*2=4 分)
 *  
 *
 * 提示：盒子的总数 n 不会超过 100。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-boxes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 采用递归+暴力求解
 */
public class Solution1 {
    public int removeBoxes(int[] boxes) {
        // 提前判空
        if (boxes.length == 0){
            return 0;
        }
        // 积分
        int res = 0;

        for (int i = 0,j=i+1; i < boxes.length; i++) {

            // 从头开始进行取走不同颜色的盒子
            // 当j小于数组长度时判断接下去有多少个相同颜色的盒子
            while (j<boxes.length&&boxes[i] == boxes[j]){
                j++;
            }
            // 去除掉一定连续数量的盒子后的新数组
            // 初始化大小
            int[] newboxes = new int[boxes.length-(j-i)];
            // 给新数组赋值
            for (int k = 0,p=0; k < boxes.length; k++) {
                // k代表了新数组中盒子在老数组中的顺序
                if (k == i)
                    k=j;
                if (k<boxes.length)
                    newboxes[p++] = boxes[k];
            }
            // 递归遍历所有可能求出最大值
            res = Math.max(res,removeBoxes(newboxes)+(j-i)*(j-i));
        }


        return res;
    }

    public static void main(String[] args) {
//        int a[] = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        int a[] = {1,2,1,2,1};
        int i = new Solution1().removeBoxes(a);
        System.out.println(i);
    }
}
