package priv.wzb.datastructure.linkedlist;

/**
 * @author Satsuki
 * @time 2019/10/29 20:56
 * @description:
 * 约瑟夫（Josepfu）问题
 * 单向环形链表
 */
public class Josepfu {
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
        circleSingleLinkedList.addBoy(125);
        circleSingleLinkedList.showBoy();
        circleSingleLinkedList.countBoy(1,2,125);


    }
}

// 创建一个单向环形链表
class CircleSingleLinkedList{
    // 创建一个first节点，当前没有编号
    private Boy first = new Boy(-1);

    // 添加小孩，构建成一个环形链表
    public void addBoy(int nums){
        // nums 做一个数据校验
        // 数据校验有时候不只是校验形参还要校验当前类自己的参数
        if (nums < 1){
            System.out.println("nums 的值不正确");
        }

        // 辅助指针，帮忙便利环形链表
        Boy curBoy = null;
        // 使用for创建环形链表
        for (int i = 1; i <= nums; i++) {
            // 根据编号，创建小孩节点
            Boy boy = new Boy(i);
            // 如果是第一个小孩
            if (i == 1){
                first = boy;
                first.setNext(first); // 构成环
                curBoy = first; // 让curBoy指向第一个小孩
            }else {
                // 创建下一个boy
                curBoy.setNext(boy);
                // 构成环形链表
                boy.setNext(first);
                // 辅助指针后移
                curBoy = curBoy.getNext();
            }
        }
        
    }

    // 遍历
    public void showBoy(){
        //判断链表是否为空
        if (first == null){
            System.out.println("链表为空，没有任何小孩");
            return;
        }
        // 因为first不能动，所以使用辅助指针
        Boy curBoy = first;
        while (true){
            System.out.printf("小孩的编号%d \n",curBoy.getNo());
            // 判断是否到最后
            if (curBoy.getNext()==first){
                break;
            }
            // 后移
            curBoy = curBoy.getNext();
        }
    }

    // 根据用户的输入，计算出小孩出圈的顺序

    /**
     *
     * @param startNo 表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums 表示最初由多少小孩在圈中
     */
    public void countBoy(int startNo,int countNum,int nums){
        // 数据校验
        // 当链表为空（first== null
        // 当输入的开始编号小于1
        // 当开始编号大于圈内小孩个数
        // 出错返回
        if (first == null || startNo<1 || startNo > nums){
            System.out.println("参数输入有误,请重新输入");
            return;
        }

        // 创建辅助指针，帮助完成小孩出圈
        Boy helper = first;
        // 需要创建一个辅助指针，指向环形链表的最后一个节点
        while (true){
            if (helper.getNext() == first){
                // helper说明指向了最后小孩节点
                break;
            }

            helper = helper.getNext();
        }
        // 小孩报数前，先让first和helper移动k - 1 次
        // 其实helper指向了first的前一个节点
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        // 当小孩报数时，让first和helper指针同时移动m-1次，然后出圈
        // 这里是一个循环操作，知道圈中只有一个节点
        while (true){
            // 检测，如果圈中只有一个节点
            if (helper == first){
                break;
            }
            // 让first和helper移动countNum -1次
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            // 此时first指向的节点，就是要出圈的小孩节点
            System.out.printf("小孩%d出圈\n",first.getNo());
            // 删除小孩 将first指向的小孩出圈
            // first指向下一个节点
            first = first.getNext();
            // 让待删除节点的上一个节点指向first
            // 就是让待删除的节点从循环链表中移除让它不可达
            helper.setNext(first);
        }

        System.out.println("最后留在圈中的小孩：" + first.getNo());

    }
}

// 创建一个Boy类，表示一个节点
class Boy{
    private int no; // 编号
    private Boy next; // 指向下一个节点，默认null

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
