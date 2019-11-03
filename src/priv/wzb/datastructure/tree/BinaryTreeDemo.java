package priv.wzb.datastructure.tree;

/**
 * @author Satsuki
 * @time 2019/11/1 17:20
 * @description:
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "无用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        // 先手动创建，后面再递归
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);
        // pre:12354,infix:21534,post:25431

        // 测试
        System.out.println("前序：");
        binaryTree.preOrder();
        System.out.println("infix：");
        binaryTree.infixOrder();
        System.out.println("post：");
        binaryTree.postOrder();

        HeroNode node;
        System.out.println("查找");
        System.out.println("preOrderSearch");
        node = binaryTree.preOrderSearch(5);
        if (node!=null){
            System.out.println(node.toString());
        }
        System.out.println("infixOrderSearch");
        node = binaryTree.infixOrderSearch(5);
        if (node!=null){
            System.out.println(node.toString());
        }
        System.out.println("postOrderSearch");
        node = binaryTree.postOrderSearch(5);
        if (node!=null){
            System.out.println(node.toString());
        }

        System.out.println("删除节点");
        System.out.println("before del");
        binaryTree.preOrder();
//        binaryTree.delNode(5);
        binaryTree.delNode(3);
        System.out.println("after del");
        binaryTree.preOrder();
    }
}

// 定义一颗二叉树
class BinaryTree{
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    // 前序
    public void preOrder(){
        if (this.root!=null){
            this.root.preOrder();
        }else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    // 中序
    public void infixOrder(){
        if (this.root!=null){
            this.root.infixOrder();
        }else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    // 后序
    public void postOrder(){
        if (this.root!=null){
            this.root.postOrder();
        }else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    public HeroNode preOrderSearch(int no){
        if (root != null){
            return root.preOrderSearch(no);
        }
        return null;
    }
    public HeroNode infixOrderSearch(int no){
        if (root != null){
            return root.infixOrderSearch(no);
        }
        return null;
    }
    public HeroNode postOrderSearch(int no){
        if (root != null){
            return root.postOrderSearch(no);
        }
        return null;
    }

    public void delNode(int no){
        if (root!=null){
            if (root.getNo() == no){
                root =null;
            }else {
                root.delNode(no);
            }

        }else {
            System.out.println("null tree can't del");
        }
    }
}

