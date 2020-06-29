package priv.wzb.leet_code.median_of_two_sorted_arrays_04;

/**
 * @author Satsuki
 * @time 2019/7/19 14:55
 * @description:给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 */
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length+nums2.length;
        int medIndex,medIndex1;
        double median=0;
        if(len%2==0){
            //中位数下标 如果是偶数个则有两个下标
            medIndex = len /2;

            medIndex1 = medIndex+1;
            //i和j分别是nums1和nums2的下标
            for (int i = 0,j=0; i+j < len; ) {
                //以大小为基准按顺序遍历
                if((i<nums1.length&&j<nums2.length&&nums1[i]<nums2[j])|| (j==nums2.length&&i!=nums1.length)){
                    //如果第一个数组的第i个数小于第二个数组的第j个数字
                    //
                    if(i+j+1==medIndex){
                        median+=nums1[i];
                    }
                    if(i+j+1==medIndex1){
                        median+=nums1[i];
                        break;
                    }

                    i++;
                }else if((i<nums1.length&&j<nums2.length&&nums2[j]<=nums1[i]) || (j!=nums2.length&&i==nums1.length)){

                    if(i+j+1==medIndex){
                        median+=nums2[j];
                    }
                    if(i+j+1==medIndex1){
                        median+=nums2[j];
                        break;
                    }

                    j++;
                }
            }

            median /=2.0;
        }else{
            //奇数个只有一个中位数下标
            //中位数下标
//            double test = len/2+0.5;
            medIndex = new Double(len/2.0+0.5).intValue();
            for (int i = 0,j=0; i+j < len; ) {
                if((i<nums1.length&&j<nums2.length&&nums1[i]<nums2[j])|| (j==nums2.length&&i!=nums1.length)){
                    if(i+j+1==medIndex){
                        median+=nums1[i];
                        break;
                    }
                    i++;
                }else if((i<nums1.length&&j<nums2.length&&nums2[j]<=nums1[i]) || (j!=nums2.length&&i==nums1.length)){
                    if(i+j+1==medIndex){
                        median+=nums2[j];
                        break;
                    }
                    j++;

                }
            }
        }

        return median;
    }

    public static void main(String[] args) {
        int[] nums1= new int[]{1,3};
        int[] nums2 = new int[]{2};

//        int[] nums1= new int[]{1,2};
//        int[] nums2 = new int[]{3,4};

        System.out.println(new Solution().findMedianSortedArrays(nums1,nums2));

    }
}
