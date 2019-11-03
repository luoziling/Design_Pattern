package priv.wzb.sort.Insert;

import priv.wzb.sort.Util;

/**
 * @author Satsuki
 * @time 2019/10/31 17:27
 * @description:
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] a = {0,10,9,2,4,6,4,3,1};
        insertSort(a);
        Util.printf(a);

//        int[] arr = Util.getTestArr();
//        long now = System.currentTimeMillis();
////        insertSort(arr);
//        insertSort1(arr);
//        long last = System.currentTimeMillis();
//        System.out.println("花费时间：" + (last-now));

    }

    public static void insertSort(int[] arr){
        int n = arr.length;
        int j; // insertIndex
        for (int i = 1; i < n; i++) {
            int a = arr[i];
            for ( j = i-1; j >= 0 && arr[j]>a; j--) {
//            for ( j = i-1; j < i; j++) {
//                if (arr[j] < a){
//
//                }
                arr[j+1] = arr[j];

            }
            System.out.println("j:" + j);


//            if (j+1!=i){
////                continue;
//                j = j+1;
//                arr[j] = a;
//            }

            // Todo：为什么这边要加一（已经理解）shell排序却不用加gap
            // 希尔排序中的j从未小于0
            // 这里的+1是为了补正差值

            j = j+1;
            arr[j] = a;

        }


//        // 第一轮
//        // 定义待插入的数
//        int insertVal = arr[1];
//        int insertIndex = 1-1;
//        // 给insertValue找到要插入的位置
//        // 保证不越界
//        // 待插入的数未找到适当位置
//        while (insertIndex >= 0 && insertVal<arr[insertIndex]){
//            // 将insertIndex后移
//            arr[insertIndex + 1] = arr[insertIndex];
//            insertIndex--;
//        }
//
//        // 当退出while循环时找到插入位置
////        insertIndex +1
//        arr[insertIndex +1 ] = insertVal;

    }


    //插入排序
    public static void insertSort1(int[] arr) {
        int insertVal = 0;
        int insertIndex = 0;
        //使用for循环来把代码简化
        for(int i = 1; i < arr.length; i++) {
            //定义待插入的数
            insertVal = arr[i];
            insertIndex = i - 1; // 即arr[1]的前面这个数的下标

            // 给insertVal 找到插入的位置
            // 说明
            // 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
            // 2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
            // 3. 就需要将 arr[insertIndex] 后移
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
                insertIndex--;
            }
            // 当退出while循环时，说明插入的位置找到, insertIndex + 1
            // 举例：理解不了，我们一会 debug
            //这里我们判断是否需要赋值
            if(insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }

            //System.out.println("第"+i+"轮插入");
            //System.out.println(Arrays.toString(arr));
        }


		/*


		//使用逐步推导的方式来讲解，便利理解
		//第1轮 {101, 34, 119, 1};  => {34, 101, 119, 1}


		//{101, 34, 119, 1}; => {101,101,119,1}
		//定义待插入的数
		int insertVal = arr[1];
		int insertIndex = 1 - 1; //即arr[1]的前面这个数的下标

		//给insertVal 找到插入的位置
		//说明
		//1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
		//2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
		//3. 就需要将 arr[insertIndex] 后移
		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}
		//当退出while循环时，说明插入的位置找到, insertIndex + 1
		//举例：理解不了，我们一会 debug
		arr[insertIndex + 1] = insertVal;

		System.out.println("第1轮插入");
		System.out.println(Arrays.toString(arr));

		//第2轮
		insertVal = arr[2];
		insertIndex = 2 - 1;

		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}

		arr[insertIndex + 1] = insertVal;
		System.out.println("第2轮插入");
		System.out.println(Arrays.toString(arr));


		//第3轮
		insertVal = arr[3];
		insertIndex = 3 - 1;

		while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}

		arr[insertIndex + 1] = insertVal;
		System.out.println("第3轮插入");
		System.out.println(Arrays.toString(arr)); */

    }
}
