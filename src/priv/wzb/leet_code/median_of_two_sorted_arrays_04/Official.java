package priv.wzb.leet_code.median_of_two_sorted_arrays_04;

/**
 * @author Satsuki
 * @time 2019/7/19 16:04
 * @description:
 */
public class Official {
    public double findMedianSortedArrays(int[] A, int[] B) {
        //初始化数组长度
        int m = A.length;
        int n = B.length;
        //确保m<=n
        if(m>n){//to ensure m<=n
            int[] temp = A;
            A=B;
            B=temp;
            int tmp = m;
            m=n;
            n=tmp;
        }

        //halfLen应该是中心点位置
        // halfLen做+1处理是为了让结果保证指向中位数，或者如果是偶数个数指向中位数后面一位
        //iMax是较短数组的长度
        int iMin=0,iMax=m,halfLen = (m+n+1)/2;
        while (iMin<=iMax){
            //i代表较短数组的中心点
            int i = (iMin + iMax)/2;
            //j代表较短数组中心点与整体中心点的距离
            int j = halfLen-i;
            // 使用二分查找
            if(i<iMax&&B[j-1]>A[i]){
                iMin = i+1;//i is too small
            }else if (i>iMin&&A[i-1 ]>B[j]){
                iMax = i-1; //i is too big
            }else {
                //i is perfect
                int maxLeft=0;
                if(i==0){
                    maxLeft = B[j-1];
                }else if (j==0){
                    maxLeft = A[i-1];
                }else {
                    maxLeft = Math.max(A[i-1],B[j-1]);
                }
                //如果数组长度为单数
                if((m+n)%2==1){
                    return maxLeft;
                }

                int minRight = 0;
                if(i==m){
                    minRight = B[j];
                }else if (j==n){
                    minRight = A[i];
                }else {
                    minRight = Math.min(B[j],A[i]);
                }
                return (maxLeft+minRight)/2.0;
            }


        }
        return 0.0;

    }




    public static void main(String[] args) {
        int[] nums1= new int[]{1,3};
        int[] nums2 = new int[]{2};

//        int[] nums1= new int[]{1,2};
//        int[] nums2 = new int[]{3,4};

        System.out.println(new Official().findMedianSortedArrays(nums1,nums2));

    }
}
