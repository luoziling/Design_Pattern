package priv.wzb.datastructure.tree;

/**
 * @author Satsuki
 * @time 2019/11/2 15:35
 * @description:
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,5,6,7};
        int[] arr = {1,3,6,8,10,14};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
//        arrBinaryTree.preOrder(0);
//        arrBinaryTree.preOrder();
        arrBinaryTree.infixOrder(0);
//        arrBinaryTree.postOrder(0);

    }
}

// 编写一个ArrayBinaryTree,实现顺序存储二叉树遍历
class ArrBinaryTree{
    // 存储数据节点的数组
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    // 重载preOrder
    public void preOrder(){
        this.preOrder(0);
    }
    // 编写一个方法，完成顺序存储二叉树的前序遍历

    /**
     *
     * @param index 数组的下标
     */
    public void preOrder(int index){
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能进行前序遍历");
        }
        // 输出当前这个元素
        System.out.println(arr[index]);
        // 向左递归遍历
        if (2*index+1<arr.length){
            // 提前判断
            preOrder(2*index+1);
        }
        // 向右递归
        if (index * 2 + 2<arr.length){
            // 判断不越界
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder(int index){
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能进行前序遍历");
        }

        // 向左递归遍历
        if (2*index+1<arr.length){
            // 提前判断
            infixOrder(2*index+1);
        }
        // 输出当前这个元素
        System.out.println(arr[index]);
        // 向右递归
        if (index * 2 + 2<arr.length){
            // 判断不越界
            infixOrder(index * 2 + 2);
        }
    }

    public void postOrder(int index){
        // 如果数组为空，或者arr.length = 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能进行前序遍历");
        }

        // 向左递归遍历
        if (2*index+1<arr.length){
            // 提前判断
            postOrder(2*index+1);
        }
        // 向右递归
        if (index * 2 + 2<arr.length){
            // 判断不越界
            postOrder(index * 2 + 2);
        }
        // 输出当前这个元素
        System.out.println(arr[index]);
    }
}
