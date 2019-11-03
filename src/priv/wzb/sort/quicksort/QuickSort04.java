package priv.wzb.sort.quicksort;

/**
 * @author Satsuki
 * @time 2019/10/31 20:29
 * @description:
 */
public class QuickSort04 {
    public static void main(String[] args) {
//        int[] arr = {-9,78,0,23,-578,70};

        long now = System.currentTimeMillis();
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 80000000);// 生成一个[0,8000000]的随机数
        }
        quickSort(arr,0,arr.length-1);
        long last = System.currentTimeMillis();
        System.out.println("花费时间：" + (last-now));
        System.out.println(arr.length);
    }

    public static void quickSort(int[] arr,int left,int right){
        int l = left;
        int r = right;
        int pivot = arr[(left+right)/2];

        int temp = 0;

        while (l<r){
            // 在pivot左边找到大于pivot的值才退出
            while (arr[l]<pivot){
                l++;
            }
            // 在pivot佑彬找到大于pivot的值
            while (arr[r]>pivot){
                r--;
            }
            // 说明pivot左边值小于等于pivot而右边全部大于等于pivot
            if (l>=r){
                break;
            }
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            // 如果交换完发现 发现arr[l] == pivot r--左移
            if (arr[l] == pivot){
                r-=1;
            }

            if (arr[r] == pivot){
                l++;
            }
        }

        if (l == r){
            l+=1;
            r-=1;
        }

        // 向左递归
        if (left<r){
            quickSort(arr,left,r);
        }

        // 向右递归
        if (right>l){
            quickSort(arr,l,right);
        }
    }
}
