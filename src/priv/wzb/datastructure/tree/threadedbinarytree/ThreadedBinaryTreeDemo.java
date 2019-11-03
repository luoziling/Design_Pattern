package priv.wzb.datastructure.tree.threadedbinarytree;

/**
 * @author Satsuki
 * @time 2019/11/2 16:03
 * @description:
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        // 测试中序线索二叉树是否正确
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        // 简单处理手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);

        node3.setLeft(node6);

        // 测试线索化
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        // 测试先序
//        threadedBinaryTree.preThreadedNodes(root);
        threadedBinaryTree.threadedNodes();
//
//        // 测试 以10号节点测试
        HeroNode left = node5.getLeft();
        System.out.println("left:" + left);
        HeroNode right = node5.getRight();
        System.out.println("right:" + right);

        // 先序遍历
//        threadedBinaryTree.preThreadList();

//        // 线索化遍历
//        System.out.println("线索化遍历");
        threadedBinaryTree.threadList(); //8,3,10,1,14,6



    }
}


