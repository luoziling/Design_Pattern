package priv.wzb.datastructure.linkedlist;

/**
 * @author Satsuki
 * @time 2019/11/11 16:17
 * @description:
 */
public class Josepfu1 {
    public static void main(String[] args) {
        // 测试构建与遍历
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.showBoy();

        // 测试一把小孩出圈是否正确
        // 从第一个小孩开始数
        // 数两下出圈
        // 一共五个小孩
        // 顺序 2 4 1 5 3
        circleSingleLinkedList.countBoy(1,2,5);
        // 测试大点的
//        circleSingleLinkedList.addBoy(125);
//        circleSingleLinkedList.showBoy();
//        circleSingleLinkedList.countBoy(1,2,125);

    }
}
class SingleCircleLinkedList{
    Boy first = new Boy(-1);

    public void add(int n){
        if (n<1){
            return;
        }
        // 辅助指针
        Boy cur = null;
        for (int i = 0; i < n; i++) {
            Boy boy = new Boy(i);
            if (i==0){
                first = boy;
                // 只有一个时候形成自身循环
                first.setNext(first);
                // cur指向第一个
                cur = first;
            }else {
                // first永远指向头节点
                // 设置新节点
                cur.setNext(boy);
                // 为了形成循环链表，新节点的next指向first
                boy.setNext(first);
                // 后移自身
                cur = cur.getNext();
            }


        }
    }


    public void show(){
        if (first == null){
            return;
        }

        Boy cur = first;
        while (cur.getNext()!=first){
            System.out.println(cur.toString());
            cur = cur.getNext();
        }
    }

    /**
     *
     * @param startNo 从第几个开始数
     * @param count 数多少出列
     * @param nums 剩余个数
     */
    public void countBoy(int startNo,int count,int nums){
        if (first == null || startNo<1 || startNo > nums){
            System.out.println("参数输入有误,请重新输入");
            return;
        }

        // 辅助指针
        Boy cur = first;
        //记录最后位置（也就是要出列的孩子的前一个位置
        while (cur.getNext()!=first){
            cur = cur.getNext();
        }
        // 前移至开始位置
        for (int i = 0; i < startNo - 1; i++) {
            cur = cur.getNext();
            first = first.getNext();
        }

        // 循环出列
        while (true){
            // 只剩一个了
            if (cur==first){
                break;
            }

            // 计数出列
            for (int i = 0; i < count - 1; i++) {
                cur = cur.getNext();
                first = first.getNext();
            }

            System.out.println(first.toString()+" 出列");
            // 出列
            first = first.getNext();
            cur.setNext(first);
        }

        System.out.println("剩余：" + first.toString());
    }
}
