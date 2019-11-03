package priv.wzb.datastructure.tree.threadedbinarytree;

/**
 * @author Satsuki
 * @time 2019/11/2 16:09
 * @description:
 */
// 定义一颗二叉树
    // 线索化二叉树
class ThreadedBinaryTree {
    private HeroNode root;

    // 为了实现线索化，需要创建指向当前节点前驱节点的指针
    // 在递归进行线索化时，pre总是保留前一个节点
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    // 遍历线索化二叉树
    public void threadList(){
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode node = root;
        while (node != null){
            // 循环找到leftType == 1的节点，第一个找到的就应该是8
            // 后面随着遍历而变化
            // 当leftType==1说明该节点是经过线索化的有效节点
            // 因为中序遍历是左根右
            // 所以按照这个顺序向左递归可以达到完全二叉树的最后一层
            // 这一层的节点被线索化了，因此可以遍历输出中序遍历的数组的顺序
            while (node.getLeftType() == 0){
                node = node.getLeft();
            }

            // 打印当前节点
            System.out.println(node);

            // 如果当前节点的右指针指向后继节点就一直输出
            while (node.getRightType() == 1){
                node = node.getRight();
                System.out.println(node);
            }

            // 替换这个遍历的节点
            // 这样做是为了让节点移动
            node = node.getRight();
        }
    }

    public void threadedNodes(){
        this.threadedNodes(root);
    }

    // 编写对二叉树进行中序线索化的方法
    public void threadedNodes(HeroNode node){
        // 如果node== null，不能线索化
        if (node == null){
            return;
        }

        // 线索化左子树
        threadedNodes(node.getLeft());
        // 线索化当前节点（有难度）
        // 处理当前节点的前驱节点
        if ( node.getLeft() == null){
            // 如果当前节点的做指针为空则指向当前节点的前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型
            // 1代表左指针指向的是前驱节点
            node.setLeftType(1);
        }

        // 处理后继节点
        // 处理后继节点是放在了下一次递归所以要用前驱节点来做
        if (pre != null && pre.getRight() == null){
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        // ！！！记录当前节点作为下一个节点的前驱节点
        pre = node;
        // 线索化右子树
        threadedNodes(node.getRight());
    }

    // 前序遍历线索化二叉树
    public void preThreadList(){
        // 定义一个变量，存储当前遍历的节点，从root开始
        HeroNode node = root;
        while (node != null){
            // 打印当前节点
//            System.out.println(node);

            // 循环找到leftType == 1的节点，第一个找到的就应该是8
            // 后面随着遍历而变化
            // 当leftType==1说明该节点是经过线索化的有效节点
//            while (node.getLeftType())
//            while (node.getLeftType() == 0){
//                node = node.getLeft();
//            }
            if (node.getLeftType() == 0){
                System.out.println(node);
            }



            // 如果当前节点的右指针指向后继节点就一直输出
            while (node.getRightType() == 1){
                System.out.println(node);
                node = node.getRight();
            }

            // 替换这个遍历的节点
            // 这样做是为了让节点移动
//            node = node.getRight();
            if (node.getLeftType()==0){
                node = node.getLeft();
            }else {
                System.out.println(node);
                break;
            }
        }
    }

    // 编写对二叉树进行前序线索化的方法
    public void preThreadedNodes(HeroNode node){
        // 如果node== null或者已经被线索化了,不能线索化
        if (node == null || node.getLeftType()==1 || node.getRightType()==1){
            return;
        }

//        if (node == null || node.getLeftType()==0 || node.getRightType()==0){
//            return;
//        }


        // 线索化当前节点（有难度）
        // 处理当前节点的前驱节点
        if ( node.getLeft() == null){
            // 如果当前节点的做指针为空则指向当前节点的前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型
            // 1代表左指针指向的是前驱节点
            node.setLeftType(1);
        }

        // 处理后继节点
        // 处理后继节点是放在了下一次递归所以要用前驱节点来做
        if (pre != null && pre.getRight() == null){
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        // ！！！记录当前节点作为下一个节点的前驱节点
        pre = node;

        // 如果该节点的左子树没被线索化
        // 线索化左子树
        if (node.getLeftType()!=1){
            preThreadedNodes(node.getLeft());
        }


        // 如果该节点的右子树没被线索化
        if (node.getRightType()!=1){
            // 线索化右子树
            preThreadedNodes(node.getRight());
        }

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

