package priv.wzb.datastructure.tree;

/**
 * @author Satsuki
 * @time 2019/11/2 16:05
 * @description:
 */
// 先创建HeroNode节点
// 数节点
class HeroNode{
    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 编写遍历
    // 前序（根左右）
    public void preOrder(){
        System.out.println(this.toString());

        if (this.left!=null){
            // 左子树不为空
            // 遍历左子树
            this.left.preOrder();
        }
        if (this.right!=null){
            // 右子树不为空
            // 遍历右子树
            this.right.preOrder();
        }
    }
    // 中序（左根右）
    public void infixOrder(){
        if (this.left!=null){
            // 左子树不为空
            // 遍历左子树
            this.left.infixOrder();
        }

        System.out.println(this.toString());

        if (this.right!=null){
            // 右子树不为空
            // 遍历右子树
            this.right.infixOrder();
        }
    }
    // 后序（左右根）
    public void postOrder(){

        if (this.left!=null){
            // 左子树不为空
            // 遍历左子树
            this.left.postOrder();
        }
        if (this.right!=null){
            // 右子树不为空
            // 遍历右子树
            this.right.postOrder();
        }
        System.out.println(this.toString());
    }



    public HeroNode preOrderSearch(int no){
        // 这里才是真正比较的地方
        System.out.println("pre count");
        if (this.no == no){
            return this;
        }

        HeroNode resNode = null;
        if (this.left!=null){
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }
        if (this.right!=null){
            resNode = this.right.preOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }
        return resNode;
    }

    public HeroNode infixOrderSearch(int no){



        HeroNode resNode = null;
        if (this.left!=null){
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }

        System.out.println("infix count");
        if (this.no == no){
            return this;
        }

        if (this.right!=null){
            resNode = this.right.infixOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }
        return resNode;
    }

    public HeroNode postOrderSearch(int no){


        HeroNode resNode = null;
        if (this.left!=null){
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }
        if (this.right!=null){
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null){
            return resNode;
        }

        System.out.println("post count");
        if (this.no == no){
            return this;
        }
        return resNode;
    }

    public void delNode(int no){
        System.out.println("delno=" + no);
        if (this.left!=null && this.left.no == no){
            this.left = null;
            return;
        }
        if (this.right!=null && this.right.no == no){
            this.right = null;
            return;
        }

        if (this.left!=null){
            this.left.delNode(no);
        }

        if (this.right!=null){
            this.right.delNode(no);
        }

    }

}

