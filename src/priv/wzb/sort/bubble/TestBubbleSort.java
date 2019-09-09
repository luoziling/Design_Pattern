package priv.wzb.sort.bubble;

/**
 * @author Satsuki
 * @time 2019/6/19 14:07
 * @description:https://www.cnblogs.com/onepixel/p/7674659.html
 */
public class TestBubbleSort {
    public static void sort(int[] a){
        int temp=0;
        //一共要排序n-1次
        for (int i = 0; i < a.length-1; ++i) {
            //每次要比较n-i-1次
            for (int j = 0; j < a.length-i-1; ++j) {
                //从小到大排序
//                if(a[j]>a[j+1]){
                //从大到小排序
                if(a[j]<a[j+1]){
                    temp = a[j];
                    a[j]=a[j+1];
                    a[j+1] = temp;
                }
            }
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void sort1(int[] a){
        int temp=0;
        for (int i = 0; i < a.length-1; i++) {
            for (int j = 0; j < a.length-i-1; j++) {
                //从小到大
                if(a[j]>a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
        int[] a = {0,10,9,7,5,6,4,2,1};
//        sort(a);
        sort1(a);
    }
}
