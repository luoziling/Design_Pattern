package priv.wzb.leet_code.median_of_two_sorted_arrays;

/**
 * @author Satsuki
 * @time 2019/7/19 14:55
 * @description:
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 */
public class Solution3 {
    // 采用折半查找解决，通过中位数的定义将两个数组都分为左右两部分
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 定义两个长度
        int m = nums1.length;
        int n = nums2.length;
        // 保证m<=n
        if (m>n){
            // 取反递归，记得return
            return findMedianSortedArrays(nums2,nums1);
        }

        // 定义分割线的左侧与右侧
        // 其实iMin和iMax代表的是在折半查找用到的数组的最左侧和最右侧

        int iMin = 0,iMax = m;
        // 随着折半查找的继续在不停的改动，一般查找到了正确的分割点也就是i在正确的位置就可以继续执行
        while (iMin<=iMax){
            // 折半分割两个数组
            // +1为了屏蔽奇偶差距
            int i = (iMin+iMax)/2;
            // 因为i+j = m-i+n-j:偶数
            // 或者i+j = m-i+n-j+1:奇数左侧数组回比右侧数组少一
            // 由此的j = (m+n+1)/2 - i
            // 使ij产生联系避免出现两条分割线带来重复
            int j = (m+n+1)/2-i;
            // 根据不同情况进行分析
            // A数组的左侧最大值大于B数组右侧最小值
            // 首先判定不能越界
            if (i!=0&&j!=n&&nums1[i-1]>nums2[j]){
                // 左移
                iMax = i-1;
            }else if (j!=0&&i!=m&&nums2[j-1]>nums1[i]){
                //右移
                iMin = i+1;
            }else {
                // 其实上述就是在对两个数组进行分割
                // 分割好了
                // 找左侧最大值与右侧最小值
                // 先找左侧最大值
                int maxLeft;
                // 再次分情况：奇偶
                // 奇数情况
                // 分隔符在数组A或数组B的最前面
                if (i==0){
                    // 分割在了数组A的最前面
                    // 左侧最大值就是数组B的左半部分的最大值
                    maxLeft = nums2[j-1];
                }else if (j == 0){
                    // 分割在了数组B的最前面
                    // 左侧最大值就是数组A的左半部分的最大值
                    maxLeft = nums1[i-1];
                }else {
                    //正常情况取两个数组左侧部分的最大值
                    maxLeft = Math.max(nums1[i-1],nums2[j-1]);
                }

                // 数组总长度为奇数
                // 直接返回左侧最大值即可
                if ((m+n)%2==1){
                    return maxLeft;
                }

                // 数组长度是偶数
                // 找右侧最小值
                int minRight = 0;
                // 分三种情况
                // 分割线在数组的最后面
                if (i==m){
                    // 分割线在数组A的最后面
                    // 此时右侧最小值为数组B的右侧的最小值
                    minRight = nums2[j];
                }else if (j==n){
                    // 分割线在数组B的最后面
                    // 此时右侧最小值为数组A的右侧的最小值
                    minRight = nums1[i];
                }else {
                    // 正常情况，分割在两个数组的中间
                    // 找两个数组右侧部分的最小值
                    minRight = Math.min(nums1[i],nums2[j]);
                }
                return (maxLeft+minRight)/2.0;
            }
        }
        return 0.0;

    }

    public static void main(String[] args) {
//        int[] nums1= new int[]{1,3};
//        int[] nums2 = new int[]{2};

//        int[] nums1= new int[]{1,2};
//        int[] nums2 = new int[]{3,4};

//        int[] nums1= new int[]{1};
//        int[] nums2 = new int[]{2,3,4};

//        int[] nums1= new int[]{1,3};
//        int[] nums2 = new int[]{2};

//        int[] nums2= new int[]{1,3};
//        int[] nums1 = new int[]{2};

//        int[] nums1= new int[]{1};
//        int[] nums2 = new int[]{2,3};

//        int[] nums1= new int[]{};
//        int[] nums2 = new int[]{2,3};

//        int[] nums1= new int[]{3};
//        int[] nums2 = new int[]{-2,-1};

        int[] nums1= new int[]{1,2};
        int[] nums2 = new int[]{-1,3};


        System.out.println(new Solution3().findMedianSortedArrays(nums1,nums2));

    }
}
