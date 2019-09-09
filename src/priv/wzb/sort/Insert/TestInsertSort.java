package priv.wzb.sort.Insert;

/**
 * @author Satsuki
 * @time 2019/6/19 14:16
 * @description:
 */
public class TestInsertSort {
    public static void sort(int arr[]){
        int i,j;
        //以第一个元素为基准默认第一个数字已经有序
        for ( i = 1; i < arr.length; i++) {
            //取下一个元素，并且记录
            int temp = arr[i];
            //从第i个位置开始向前找直到找到该元素应该存在的位置
            //该位置根据从小到大或者从大到小的不同判定也不同
            //在这里是从小到大
            for ( j = i; j > 0&& temp<arr[j-1]; j--) {
                //如果temp小于j-1个元素则将元素后移
                //j的位置就是temp应该在的位置依次向前找
                arr[j] = arr[j-1];
            }
            //将temp插入到正确的位置
            arr[j] = temp;
        }
        for (int k = 0; k < arr.length; k++) {
            System.out.println(arr[k]);
        }
    }
    public static void sort1(int arr[]){
        int j;
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            for (j = i; j >0 && temp<arr[j-1]; j--) {
                arr[j]=arr[j-1];
            }
            arr[j]=temp;
        }
        for (int k = 0; k < arr.length; k++) {
            System.out.println(arr[k]);
        }
    }
    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
//        sort(a);
        sort1(a);
    }
}
