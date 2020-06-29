package priv.wzb.leet_code.median_of_two_sorted_arrays_04;

/**
 * @author Satsuki
 * @time 2019/7/19 14:55
 * @description:给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * 参考：
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
 *
 */
public class Solution2 {

    /**
     * 暴力解法，无需遍历全部数组只需要遍历len/2+1次
     * 但是时间复杂度仍然是O（m+n）
     * @param A
     * @param B
     * @return
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        // 总长度
        int len = m+n;
        // left代表前一个
        // right代表当前正在遍历的
        int left = -1,right = -1;
        int aStart=0,bStart=0;
//        for (int i = 0; i < len / 2; i++) {
        // 一定要加上等于 不然无法遍历到中位数的位置
        for (int i = 0; i <= len / 2; i++) {
            // 用left保存上一次遍历获得的数
            left = right;
            // 如果aStart未到终点，且（A的当前位置小于B数组的当前位置或者b数组已经到了尽头）
            if (aStart<m&&(bStart>=n||A[aStart]<B[bStart])){
                // 记录当前数字
                right = A[aStart++];
            }else {
                right = B[bStart++];
            }
        }
        // 奇偶判定
        // 将最后一个二进制与1进行与操作如果是偶数那么肯定结果就是0
        if ((len&1) == 0){
            return (left+right)/2.0;
        }else {
            return right;
        }
    }

    /**
     * 大佬思路：将找中间数转化为找第k小的数字
     * 每次取出k/2个数字比较logK次即可
     * 满足时间需求
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        // left和right都指向中间数的下标
        // 如果总数是奇数个数字那么left和right所指向的数字都是相同的
        // 如果是偶数个数字那么会指向胡同的数字都是中位数要相加/2也就有了下面return的地方进行两边处理
        // 这里的left和right并不是两个数组中的下标之类的就单纯的指向了中位数
        // left和right并不是指向的下标而是指向的第几个数字所以+1和+2
        // left指向前一个 right指向后一个
        int left = (n+m+1)/2;
        // 要+2处理
        int right = (n+m+2)/2;
        // 奇偶合并，奇数仍然求两次因为*0.5所以毫无影响
        return (getKth(nums1,0,n-1,nums2,0,m-1,left) + getKth(nums1,0,n-1,nums2,0,m-1,right))*0.5;
    }

    /**
     * 获得第k小的数字
     * @return
     */
    private int getKth(int[] nums1,int start1,int end1,int[] nums2,int start2,int end2,int k){



        // len1和len2代表两个数组剩下的长度
        int len1 = end1-start1+1;
        int len2 = end2-start2+1;
        // 让len1的长度小于len2,这样就能保证如果数组空了，一定是len1
        if (len1>len2) return getKth(nums2,start2,end2,nums1,start1,end1,k);
        // 极端情况
        // 如果一个数组为空
        if (len1 == 0) return nums2[start2+k -1];

        // 极端情况在第一次找或者查找过程中发现要进行查找的中位数的位置在第一个那就返回较小的
        if (k == 1) return Math.min(nums1[start1],nums2[start2]);



        // 对于极端情况的处理，如果一端的数组中的数字剩余个数要小于k/2那么就取最右边的数作为要进行比较的数值即可
        int i = start1+Math.min(len1,k/2)-1;
        int j = start2 + Math.min(len2,k/2)-1;

        if (nums1[i]>nums2[j]){
            // 当nums1中的k/2的下标较大
            // 就说明可以取出num2中的前k/2个数字
            // k因为已经排除了一定的数字从而进行缩减
            return getKth(nums1,start1,end1,nums2,j+1,end2,k-(j-start2+1));
        }else {
            // 取出nums1中的数字
            return getKth(nums1,i+1,end1,nums2,start2,end2,k-(i-start1+1));
        }



    }

//    private int getKth1(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
//        int len1 = end1 - start1 + 1;
//        int len2 = end2 - start2 + 1;
//        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
//        if (len1 > len2) return getKth1(nums2, start2, end2, nums1, start1, end1, k);
//        if (len1 == 0) return nums2[start2 + k - 1];
//
//        if (k == 1) return Math.min(nums1[start1], nums2[start2]);
//
//        int i = start1 + Math.min(len1, k / 2) - 1;
//        int j = start2 + Math.min(len2, k / 2) - 1;
//
//        if (nums1[i] > nums2[j]) {
//            return getKth1(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
//        }
//        else {
//            return getKth1(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
//        }
//    }

    /**
     * 最贴近官方的解法通过中值的定义，将两个数组分为四部分，再对这四个部分进行调整找出中值
     * 在这个过程中进行了奇偶屏蔽，切割点移动等操作
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        // 确保m<=n
        if (m>n){
            // 记得return,否则不会在此处返回，会继续执行直到return再返回递归
            return findMedianSortedArrays2(nums2,nums1);
        }
        int iMin = 0,iMax = m;
        // iMin代表边界线左边，iMax代表边界线右边
        while (iMin<=iMax){
            // i取中值，折半查找
            int i = (iMin+iMax) /2;
            // 用AB代表两个数组
            // 如果数组A和B总长度是偶数
            // 均分的方式左边等于右边:i和j代表左边数组的极限下标
            // i+j = m-i+n-j
            // 此时可推导
            // j = (m+n)/2 -i
            // 如果数组A和B的总长度是奇数
            // 我们使左边数组比右边数组大1
            // 公式：
            // i+j = m-i+n-j+1
            // 此时可推导j
            // j = (m+n+1)/2 -i
            // 因为在+1处理后会/2操作（1/2=0.5如果是int会被削为0）
            // 所以对于偶数的情况使用奇数的公式也不错产生影响
            // 从而推导得j的公式
            int j = (m+n+1)/2-i;
            // 当数组A和B的总长度是偶数
            // 我们可以保证左半部分长度等于右边部分长度
            // 上述ij关联公式就是用于解决此问题
            // 左半部分的最大值小于等于右半部分的最小值
            // max(A[i-1],B[j-1])<=min(A[i],B[j])
            // 中位数就可以表示为
            // （左半部分最大值+右半部分最小值）/2
            //（max(A[i-1],B[j-1])+min(A[i],B[j])）/2
            // 当AB总长度是奇数
            // 如果我们保证左半部分长度比右半部分长度大1
            // 且 左半部分最大值小于右半部分最小值
            // 那么中位数就是左半部分最大值（也就是左半部分比右半部分多出来的值）
            // max(A[i-1],B[j-1])

            //为了保证max(A[i-1],B[j-1])<=min(A[i],B[j])
            //分三种情况分析
            // 当B[j-1]>A[i]时也就是B数组的左半部分最大值大于A数组右半部分最小值
            // 为了保证不越界我们就需要保证j!=0,i!=m
            // 此时我们需要将i增大使A数组的分割线右移
            // 十分幸运，根据上面的分析IJ是有关联的所以只需要使i增大即可
            if (j!=0&& i!=m && nums2[j-1]>nums1[i]){
                // i需要增大
                // 需要右移边界线就是把边界线左边右移
                iMin = i+1;
            }else if (i!=0&&j!=n&&nums1[i-1]>nums2[j]){
                // A[i-1]>B[j]时也就是A数组的左半部分最大值大于B数组的右半部分最小值
                // 要保证不越界i!=0,j!=n
                // 此时需要减小i，左移分割线
                // 需要左移边界线就是把边界线右边左移
                iMax = i-1;
            }else {
                //达到了分割的要求，但是需要将极端情况单独拿出来考虑
                int maxLeft = 0;
                // 当i=0或者j=0那么就切在了数组A或B的最前面
                // 此时左半部分当i=0时最大值就是B[j-1]
                if (i==0){
                    maxLeft = nums2[j-1];
                } else if (j==0) {
                    // 此时当j==0那么最大值就是A[i-1]
                    maxLeft = nums1[i-1];
                }else {
                    // 此时切在了两个数组中间没有产生极端情况
                    // 那么取两个数组左半部分的最大值即可
                    maxLeft = Math.max(nums1[i-1],nums2[j-1]);
                }

                // 总长度为奇数的情况
                if ((m+n)%2==1){
                    // 不需要考虑右半部分
                    // 左半部分的最大值即为中位数
                    return maxLeft;
                }
                int minRight = 0;
                // 在求右半部分最大值时也需要考虑两种极端情况
                // 中间线切在了某个数组的末尾
                // 那么最大值就是剩下一个数组的右半部分的最小值
                if (i== m){
                    // 切在了A数组的最后面
                    minRight = nums2[j];
                }else if (j==n){
                    // 切在了B数组的最后
                    minRight = nums1[i];
                }else {
                    //正常情况切在两个数组中间
                    // 此时求两个数组右半部分的最小值即可
                    minRight = Math.min(nums1[i],nums2[j]);
                }

                // 偶数的情况返回
                return (maxLeft+minRight)/2.0;
            }
        }
        // 未找到的情况
        return 0.0;
    }

    public double findMedianSortedArrays3(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return findMedianSortedArrays3(B,A); // 保证 m <= n
        }
        int iMin = 0, iMax = m;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = (m + n + 1) / 2 - i;
            if (j != 0 && i != m && B[j-1] > A[i]){ // i 需要增大
                iMin = i + 1;
            }
            else if (i != 0 && j != n && A[i-1] > B[j]) { // i 需要减小
                iMax = i - 1;
            }
            else { // 达到要求，并且将边界条件列出来单独考虑
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; } // 奇数的话不需要考虑右半部分

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0; //如果是偶数的话返回结果
            }
        }
        return 0.0;
    }



    public static void main(String[] args) {
//        int[] nums1= new int[]{1,3};
//        int[] nums2 = new int[]{2};

//        int[] nums1= new int[]{2};
//        int[] nums2 = new int[]{};

//        int[] nums1= new int[]{1,2};
//        int[] nums2 = new int[]{3,4};
        int[] nums1= new int[]{3,4};
        int[] nums2 = new int[]{1,2};

        System.out.println(new Solution2().findMedianSortedArrays2(nums1,nums2));

    }
}
