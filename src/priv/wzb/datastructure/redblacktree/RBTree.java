package priv.wzb.datastructure.redblacktree;

/**
 * @author Satsuki
 * @time 2019/9/17 20:21
 * @description:
 */
public class RBTree<T extends Comparable<T>> {
    //根节点
    private RBTNode<T> mRoot;

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    //内部类
    public class RBTNode<T extends  Comparable<T>>{
        //颜色
        boolean color;
        //关键值
        T key;
        //左孩子
        RBTNode<T> left;
        //右孩子
        RBTNode<T> right;
        //父节点
        RBTNode<T> parent;

        public RBTNode(T key, boolean color,RBTNode<T> parent,RBTNode<T> left,RBTNode<T> right){
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 对红黑树的节点x进行左旋
     * 左旋示意图（对节点x左旋）
     * @param x
     *
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     */
    private void leftRotate(RBTNode<T> x){
        //设置x的右孩子为y
        RBTNode<T> y = x.right;

        //将 “y的左孩子” 设为 “x的右孩子”
        x.right = y.left;

        // 如果y的左孩子非空，将“x”设为 “y的左孩子的父亲”
        if (y.left != null){
            y.left.parent = x;
        }

        //将"x的父亲" 设为 "y的父亲"
        y.parent = x.parent;

        //如果x的父亲是空节点，则将y设为根节点
        if (x.parent == null){
            this.mRoot = y;
        }else {
            //如果x是它父亲的左孩子，则将y设为 x的父节点的左孩子
            if (x.parent.left == x){
                x.parent.left = y;
            }else{
                //如果x是它父亲的右孩子，则将y设为 x的父节点的右孩子
                x.parent.right = y;
            }

        }

        //将 x 设为y的左孩子
        y.left = x;
        //将 x 的父节点设为 y
        x.parent = y;
    }


    /**
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                           lx   y
     *       / \                                   / \                   #
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RBTNode<T> y){
        //设置x为当前节点的左孩子
        RBTNode<T> x = y.left;


        //将y的父节点设为x的父节点
        x.parent = y.parent;
        //查看y的父节点是否为空是的话设置x为空且为根节点
        //否则x为y的父节点左孩子
        if (y.parent == null){
            x.parent = null;
            this.mRoot = x;
        }else {

            if (y == y.parent.left){
                //如果y是它父亲的左子节
                y.parent.left = x;
            }else{
                y.parent.right = x;
            }

        }

        y.left = x.right;
        //如果非空将x的右子节点设为y的左子节点
        //如果x的右孩子不为空，将y设为x的右孩子的父亲
        if (x.right!=null){
            x.right.parent = y;
        }





        //y设为x的右子节点
        x.right = y;
        //x的父节点为y
        x.parent = y;

    }


    /**
     * 将结点插入到红黑树中
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的node
     */
    private void insert(RBTNode<T> node){
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;

        //1.将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中
        while (x != null){
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp<0){
                //当前值比插入值小向左边寻找
                x=x.left;
            }else {
                x= x.right;
            }
        }

        //上面找到了最下面的节点
        node.parent = y;
        if (y!=null){
            cmp = node.key.compareTo(y.key);
            if (cmp<0){
                y.left = node;
            }else {
                y.right = node;
            }
        }else {
            //红黑树为空
            this.mRoot = node;
        }

        //设置节点颜色
        node.color = RED;
        //修正为红黑树
    }

    public void insert(T key){
        RBTNode<T> node = new RBTNode<>(key,BLACK,null ,null,null);

        //如果新建节点失败则返回
        if (node!=null){
            insert(node);
        }
    }

    /**
     * 红黑树插入修正函数
     *
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 插入的结点        // 对应《算法导论》中的z
     */

    private void insertFixUp(RBTNode<T> node){
        RBTNode<T> parent,gparent;
        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = parentOf(node))!=null) && isRed(parent)) {
            gparent = parentOf(parent);

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.left) {
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.right == node) {
                    RBTNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBTNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        // 将根节点设为黑色
        setBlack(this.mRoot);
    }

    public void setmRoot(RBTNode<T> node){
        this.mRoot = node;
    }

    public void setBlack(RBTNode<T> node){
        node.color = BLACK;
    }

    public void setRed(RBTNode<T> node){
        node.color = RED;
    }

    public boolean isRed(RBTNode<T> node){
        if (node.color == RED){
            return true;
        }else {
            return false;
        }
    }

    public boolean isBlack(RBTNode<T> node){
        if (node.color == BLACK){
            return true;
        }else {
            return false;
        }
    }

    public RBTNode<T> parentOf(RBTNode<T> node){
        return node.parent;
    }
}
