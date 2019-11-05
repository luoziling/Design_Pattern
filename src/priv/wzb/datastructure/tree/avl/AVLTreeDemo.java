package priv.wzb.datastructure.tree.avl;

/**
 * @author Satsuki
 * @time 2019/11/3 21:50
 * @description:
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
//        int[] arr  = {4,3,6,5,7,8};
//        int[] arr  = {10,12,8,9,7,6};
        int[] arr  = {10,11,7,6,8,9};
        //创建一个AVLTree对象
        AVLTree avlTree = new AVLTree();
        //添加节点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }

        //遍历
        System.out.println("中序遍历");
        avlTree.infixOrder();


        System.out.println("在没有做平衡处理之前");
        // 树的高度
        System.out.println("树的高度"+avlTree.getRoot().height());
        System.out.println("左子树的高度"+avlTree.getRoot().leftHeight());
        System.out.println("右子树的高度"+avlTree.getRoot().rightHeight());
        System.out.println("当前的根节点="+avlTree.getRoot());
        System.out.println("根节点左节点="+avlTree.getRoot().left);
        System.out.println("根节点的右节点="+avlTree.getRoot().right);

    }
}

// 创建AVLTree
class AVLTree{
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

    // 返回左子树高度
    public int leftHeight(){
        if (left == null){
            return 0;
        }
        return left.height();
    }

    // 返回右子树高度
    public int rightHeight(){
        if (right == null){
            return 0;
        }
        return right.height();
    }

    // 返回以该节点为根节点的树的高度
    public int height(){
        return Math.max(left==null?0:left.height(),right==null?0:right.height())+1;
    }

    // 左旋转
    private void leftRotate(){
        // 创建新节点，以当前根节点的值
        Node newNode = new Node(value);
        // 把新的节点左子树设为当前节点的左子树
        newNode.left = left;
        // 把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.right = right.left;
        // 把当前节点的值替换为右子节点的值
        value = right.value;
        // 把当前节点的右子树设置为右子树的右子树
        right = right.right;
        // 把当前节点的左子节点设置为新节点
        left = newNode;
    }

    // 右旋转
    private void rightRotate(){
        // 新建节点
        Node newNode = new Node(value);
        // 将当前节点的右子树设置为新节点的右子树
        newNode.right = right;
        // 将当前节点的左子树的右子树设置为新节点的左子树
        newNode.left = left.right;
        //将当前节点的左节点的值作为当前节点的值
        value = left.value;
        // 将当前节点的左子树设置为左子树的左子树
        left = left.left;
        // 当前节点的右子树设置为新节点
        right = newNode;

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

        // 当添加完一个节点后，如果右子树的高度-左子树的高度>1
        // 左旋转
        if (rightHeight()-leftHeight()>1){
            // 如果它（根节点）的右子树的左子树高度大于它（根节点的左子树）的右子树高度
            if (right!=null && right.leftHeight()>right.rightHeight()){
                // 对当前节点的右子树进行右旋转
                right.rightRotate();
                // 再对当前节点进行旋转
            }
            leftRotate();

            // 处理完之后记得return 以免出错
            return;
//            if (right!=null&&right.rightHeight()<right.leftHeight()){
//                // 先对右子树进行
//                leftRotate();
//            }
        }

        // 当添加完一个节点后，如果左子树的高度-右子树的高度>1
        // 右旋转
        if ((leftHeight()-rightHeight())>1){
            // 如果它（根节点）的左子树的右子树高度大于它（根节点的左子树）的左子树的高度
            if (left!=null && left.rightHeight()>left.leftHeight()){
                // 先对当前节点的左节点（左子树）进行左旋转
                left.leftRotate();
                // 再对当前节点进行旋转
            }
            rightRotate();

            return;
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

