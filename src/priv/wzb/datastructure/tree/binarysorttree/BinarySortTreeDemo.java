package priv.wzb.datastructure.tree.binarysorttree;

/**
 * @author Satsuki
 * @time 2019/11/3 18:34
 * @description:
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9,2};
        BinarySortTree binarySortTree = new BinarySortTree();
        // 循环添加节点
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        // 中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");// 1 3 5 7 9 10 12
        binarySortTree.infixOrder();

        System.out.println("删除叶子节点");
        // 删除叶子节点
//        binarySortTree.delNode(2);
//        binarySortTree.delNode(1);
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        binarySortTree.delNode(1);
        binarySortTree.infixOrder();
    }
}

// 创建二叉排序树
class BinarySortTree{
    private Node root;

    public Node getRoot() {
        return root;
    }

    // 查找要删除的节点
    public Node search(int value){
        if (root == null){
            return null;
        }else {
            return root.search(value);
        }
    }

    public Node searchParent(int value){
        if (root == null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }

    // 返回以node为根节点的二叉排序树的最小节点的值
    // 删除以node为根节点的二叉排序树的最小节点
    /**
     *
     * @param node 传入的节点(二叉排序树的根节点
     * @return 返回以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node){
        Node target = node;
        // 循环查找左节点
        while (target.left!=null){
            target = target.left;
        }
        // 这时target指向了最小节点
        // 删除最小节点
        delNode(target.value);
        return target.value;
    }

    // 删除节点
    public void delNode(int value){
        if (root == null){
            return;
        }else {
            // 找要删除的targetNode
            Node targetNode = search(value);
            // 如果未找到
            if (targetNode == null){
                return;
            }
            // 如果target没有父节点（就是根节点/当前二叉排序树只有一个节点
            if (root.left == null&& root.right == null){
                root = null;
                return;
            }
            // 找父节点
            Node parentNode = searchParent(value);
            // 如果要删除的节点是叶子节点
            if (targetNode.left==null&&targetNode.right == null){
                // 判断targetNode是父节点的左子节点还是右子节点
                if (parentNode.left!=null && parentNode.left.value == value){
                    parentNode.left = null;
                }else if (parentNode.right!=null && parentNode.right.value == value){
                    parentNode.right = null;
                }
            }else if (targetNode.left!=null&&targetNode.right!=null){
                // 有左右子树
                // 从左边找最小的移动到待删除节点
                // 将最小节点删除
                int minval = delRightTreeMin(targetNode.right);
                targetNode.value = minval;
            }else {


                // 删除只有一棵子树的节点
                if (targetNode.left !=null){
                    if (parentNode!=null){
                        // 有左子节点
                        if (parentNode.left.value == value){
                            parentNode.left = targetNode.left;
                        }else{
                            parentNode.right = targetNode.left;
                        }
                    }else {
                        root = targetNode.left;
                    }

                }else {
                    if (parentNode!=null){
                        // 有右子节点
                        if (parentNode.left.value == value){
                            parentNode.left = targetNode.right;
                        }else{
                            parentNode.right = targetNode.right;
                        }
                    }else {
                        root = targetNode.right;
                    }

                }
            }

        }
    }

    // 添加节点的方法
    public void add(Node node){
        if (root == null){
            // 如果root为空
            // 直接把添加节点作为root
            root = node;
        }else {
            root.add(node);
        }
    }

    // 中序遍历
    public void infixOrder(){
        if (root != null){
            root.infixOrder();
        }else {
            System.out.println("二叉排序树为空无法遍历");
        }

    }
}

// 创建node节点
class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }


    // 查找节点
    public Node search(int value){
        if (value == this.value)
            return this;
        if (value<this.value){
            if (this.left!=null){
                return this.left.search(value);
            }else {
                return null;
            }
        }else {
            if (this.right!=null){
                return this.right.search(value);
            }else {
                return null;
            }
        }
    }

    // 查找要删除节点的父节点
    public Node searchParent(int value){
        if ((this.left!=null&&value == this.left.value) ||
                (this.right!=null&&value== this.right.value)) {
            return this;
        }else if (value<this.value && this.left!=null){
            return this.left.searchParent(value);
        }else if(value>=this.value&& this.right!=null){
            return this.right.searchParent(value);
        }
        // 没有找到父节点
        return null;

    }

    // 添加节点
    // 递归的形式添加节点，注意需要满足二叉排序树的要求
    public void add(Node node){
        if (node == null){
            return;
        }

        // 判断传入的节点的值和当前子树的根节点的关系
        if (node.value<this.value){
            // 如果左子节点为空就直接挂到左子节点即可
            // 注意要调用this.left而不是node.left
            if (this.left==null){
                this.left = node;
            }else {
                // 不为空则继续递归向左子节点添加数据直到找到一个合适的位置进行添加
                this.left.add(node);
            }
        }

        // 如果传入值大于等于根节点值其添加方式类似于 向左子节点添加
        if (node.value>=this.value){
            if (this.right==null){
                this.right = node;
            }else {
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    // 中序遍历二叉排序树时，遍历的值正好是有序的
    public void infixOrder(){
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right!= null){
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
