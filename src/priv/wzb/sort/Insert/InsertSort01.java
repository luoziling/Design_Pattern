package priv.wzb.sort.Insert;

import static priv.wzb.sort.Util.printf;

/**
 * @author Satsuki
 * @time 2019/9/14 14:03
 * @description:
 */
public class InsertSort01 {
    public static void sort(int[] arr){
        System.out.println("排序前：");
        printf(arr);
        int i,j;
        int now;//记录当前的数值 也是最后要确定排序到什么位置的数值
        for (i = 1;i<arr.length;i++){
            //记录当前需要排序的元素
            now = arr[i];
            //从该元素前一个开始找直到第一个元素为止
            for (j = i-1; j >0 ; j--) {
                //依次与要排序的元素比较
                // 通过前移或后移将要排序元素应该存放的位置找到
                if (arr[j]>now){
                    arr[j+1] = arr[j];
                }else {
                    //找到了该存放的位置跳出循环
                    System.out.println("跳出:"+j);
                    break;
                }
            }
            arr[j+1] = now;
        }
        System.out.println("排序后：");
        printf(arr);

    }



    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        sort(a);
    }
}
