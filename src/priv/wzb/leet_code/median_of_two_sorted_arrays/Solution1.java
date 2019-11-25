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
public class Solution1 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        // false为偶数，true为奇数
        boolean flag = false;
        // 中位数位置
        double median = length / 2.0;
        int intMedian = (int)median;
        // 判定数组长度为奇数还是偶数
        if (length % 2.0>0){
            flag = true;
        }
        // 分为三种情况，nums1为空，nums2为空，都不为空
        // nums1为空,在nums2中寻找即可
        // 分为奇数与偶数
        if (nums1.length == 0){
            if (flag){
                return nums2[intMedian];
            }else {
                return (nums2[intMedian-1] + nums2[intMedian])/2.0;
            }
        }

        // nums2为空,在nums1中寻找即可
        // 分为奇数与偶数
        if (nums2.length == 0){
            if (flag){
                return nums1[intMedian];
            }else {
                return (nums1[intMedian-1] + nums1[intMedian])/2.0;
            }
        }

        // nums1,nums2均不为空
        // count来记录在nums1与nums2中移动的步数i,j为两个数组下标
        int i=0,j=0,count = 0;
        double mMedian = 0.0;
        // 分为奇数与偶数
        if (nums1.length > 0&&nums2.length> 0){
            // ij的flag代表在哪个数组中移动
            boolean iFlag = false;
            boolean jFlag = false;

            // 是否产生越界
            boolean iOutFlag = false;
            boolean jOutFlag = false;

            // 是否移动过
            boolean iMove = false;
            boolean jMove = false;
            if (flag){
                while (count!=intMedian){
                    // 较小的下标向后移
                    // 防止越界以及判定另一个数组越界
                    if (jOutFlag||(!iOutFlag&&i<nums1.length&&nums1[i]<nums2[j])){
                        i++;
                        iMove=true;
                        iFlag = true;
                        jFlag = false;

                        // 防止越界 这样防止会造成
                        if (i==nums1.length){
                            i-=1;
                            // 产生了一次越界
                            iOutFlag = true;
                        }
                        // 防止特殊情况，例如nums2= new int[]{1,3};nums1 = new int[]{2};只会移动一次，应该条到另一个数组
                        // 如果另一个数组只有一个且那一个数字小于当前数字
                        if (intMedian==1&& (nums1.length==1|| (nums2.length==1)&&nums2[j]<nums1[i])){
                            iFlag = false;
                            jFlag = true;
                        }
                    }else if(iOutFlag||(!jOutFlag&&j<nums2.length&&nums1[i]>=nums2[j])) {
                        j++;
                        jMove = true;
                        jFlag = true;
                        iFlag = false;
//                        // 防止特殊情况，例如nums2= new int[]{1,3};nums1 = new int[]{2};只会移动一次，应该条到另一个数组
//                        if (intMedian==1 && (nums1.length==1|| nums2.length==1)){
//                            iFlag = true;
//                            jFlag = false;
//                        }
                        // 防止越界
                        if (j==nums2.length){
                            j-=1;
                            // 产生了一次越界
                            jOutFlag = true;
                        }
                        // 防止特殊情况，例如nums2= new int[]{1,3};nums1 = new int[]{2};只会移动一次，应该条到另一个数组
                        // 如果另一个数组只有一个且那一个数字小于当前数字
                        if (intMedian==1&& ((nums1.length==1&&nums2[j]>nums1[i])|| nums2.length==1)){
                            iFlag = false;
                            jFlag = true;
                        }
                    }
                    // 计数后移
                    count++;
                }


                if (iFlag){
                    // 下标会多加一次
                    // 不到最后就需要自减
                    if (i!=nums1.length-1){
                        // 只有移动过才需要减
                        if (iMove){
                            i--;
                        }
                    }
//                    i--;
                    //中位数在nums1中
                    mMedian = nums1[i];
                }else if (jFlag){
                    // 不到最后就需要自减
                    if (j!=nums2.length-1){
                        // 只有移动过才需要减
                        if (jMove){
                            j--;
                        }
                    }
//                    j--;
                    //中位数在nums2中
                    mMedian = nums2[j];
                }

//                return nums2[intMedian];
            }else {
                while (count!=intMedian){
//                    // 较小的下标向后移
                    if (jOutFlag||(!iOutFlag&&i<nums1.length&&nums1[i]<nums2[j])){
                        i++;
                        iMove=true;
                        iFlag = true;
                        jFlag = false;
                        // 防止特殊情况，例如nums2= new int[]{1,3};nums1 = new int[]{2};只会移动一次，应该条到另一个数组
                        // 当当前数组被遍历完了才需要条导另一个数组
//                        if (intMedian==1 && (nums1.length==1|| nums2.length==1)){
//                            iFlag = false;
//                            jFlag = true;
//                        }
                        if (i==nums1.length){
                            i-=1;
                            // 产生了一次越界
                            iOutFlag = true;
                        }
                        if (intMedian==1&& (nums1.length==1|| (nums2.length==1)&&nums2[j]<nums1[i])){
                            iFlag = false;
                            jFlag = true;
                        }

                    }else if(iOutFlag||(!jOutFlag&&j<nums2.length&&nums1[i]>=nums2[j])) {
                        j++;
                        jMove = true;
                        jFlag = true;
                        iFlag = false;
                        // 防止特殊情况，例如nums2= new int[]{1,3};nums1 = new int[]{2};只会移动一次，应该条到另一个数组
//                        if (intMedian==1&&(nums1.length==1|| nums2.length==1)){
//                            iFlag = true;
//                            jFlag = false;
//                        }
                        // 防止越界
                        if (j==nums2.length){
                            j-=1;
                            // 产生了一次越界
                            jOutFlag = true;
                        }
                        if (intMedian==1&& ((nums1.length==1&&nums2[j]>nums1[i])|| nums2.length==1)){
                            iFlag = false;
                            jFlag = true;
                        }

                    }
                    // 计数后移
                    count++;
                }
                if (iFlag){
                    // 不到最后就需要自减
                    if (i!=nums1.length-1){
                        // 只有移动过才需要减
                        if (iMove){
                            i--;
                        }
                    }
//                    i--;
                    //中位数在nums1中
                    if (i+1<nums1.length){
                        mMedian = (nums1[i] + nums1[i+1])/2.0;
                    }else {
                        mMedian = (nums1[i] + nums2[j])/2.0;
                    }

                }else if (jFlag){
                    // 不到最后就需要自减
                    if (j!=nums2.length-1){
                        // 只有移动过才需要减
                        if (jMove){
                            j--;
                        }
                    }
//                    j--;
                    //中位数在nums2中
                    if (j+1<nums2.length){
                        mMedian = (nums2[j] + nums2[j+1])/2.0;
                    }else {
                        mMedian = (nums1[i] + nums2[j])/2.0;
                    }
//                    mMedian = nums2[j];
                }
//                return (nums2[intMedian] + nums2[intMedian])/2.0;
            }
        }

        return mMedian;

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


        System.out.println(new Solution1().findMedianSortedArrays(nums1,nums2));

    }
}
