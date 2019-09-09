package priv.wzb.design_pattern.structural_pattern.adapterpattern;

/**
 * @author Satsuki
 * @time 2019/5/4 21:27
 * @description:操作适配器：适配器
 */
public class OperationAdapter implements ScoreOperation {
    private QuickSort sortObj;//定义适配者QuickSort对象
    private BinarySearch searchObj;//定义适配者BinarySearch对象

    public OperationAdapter(){
        sortObj = new QuickSort();
        searchObj = new BinarySearch();
    }
    @Override
    public int[] sort(int[] array) {
        return sortObj.quickSort(array);
//        return new int[0];
    }

    @Override
    public int search(int[] array, int key) {
        return searchObj.binarySearch(array,key);
//        return 0;
    }
}
