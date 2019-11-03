package priv.wzb.datastructure.linkedlist;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Satsuki
 * @time 2019/10/28 22:29
 * @description:
 */


public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 进行测试
        // 先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        HeroNode hero5 = new HeroNode(2, "小路", "玉麒麟111");

        // 创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        // 加入
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        // 加入按照编号的顺序
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero2);

        singleLinkedList.update(hero5);
        // 显示
        singleLinkedList.list();

        // 逆序打印
        System.out.println("逆序打印");
        reversePrint(singleLinkedList.getHead());

        System.out.println("反转:");
        reverse(singleLinkedList.getHead());
        singleLinkedList.list();

        System.out.println("删除后的情况：");

        // 删除一个节点
        singleLinkedList.del(1);
//        singleLinkedList.del(2);
//        singleLinkedList.del(3);
//        singleLinkedList.del(4);
        // 显示
        singleLinkedList.list();

        // 求单链表中有效节点的个数
        System.out.println("有效的节点个数"+getLength(singleLinkedList.getHead()));

        // 测试以下看看是否得到了倒数第K个节点
//        HeroNode res = findLastIndexNode(singleLinkedList.getHead(),1);
        HeroNode res = findLastIndexNode(singleLinkedList.getHead(),4);
        System.out.println("res=" + res);



    }

    // 逆序打印单链表（百度）
    // 方式一
    // 先逆序，再打印
    // 方式二
    // 利用栈，利用栈先进后出的特点，逆序打印
    // 此处演示方式2
    public static void reversePrint(HeroNode head){
        if (head.next == null){
            return; // 空链表不打印
        }

        // 创建栈存放链表元素完成逆序打印
        Stack<HeroNode> heroNodeStack = new Stack<>();
        // 创建辅助指针
        HeroNode temp = head;
        while (temp.next!=null){
            heroNodeStack.push(temp.next);
            temp = temp.next;// 遍历
        }

        while (heroNodeStack.size()>0){
            // 打印
            System.out.println(heroNodeStack.pop());
        }
    }


    // 反转链表(腾讯)
    // 思路
    // 定义一共reverseHead = new HeroNode()
    // 从头到尾遍历原来的链表，每遍历一共节点将其取出放在reverseHead的最前端。
    // 原来链表的head.next = reverseHead.next
    public static void reverse(HeroNode head){
        // 形参检测
        // 如果当前链表为空，或者只有一个节点，直接返回。
        // 这边不仅是形参检测更加进行了算法优化
//        if (head == null || getLength(head)==1){
//            return;
//        }
        if (head == null || head.next.next==null){
            return;
        }

        // 定义一共辅助指针（变量）帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null; // 指向当前节点【cur】的下一个节点
        // 初始化反转节点
        HeroNode reverseHead = new HeroNode(0,"","");
        // 从头到尾遍历原来的链表，每遍历一共节点将其取出放在reverseHead的最前端。
        while (cur!=null){
            // 先暂时保存当前节点的下一个节点因为后面有用
            next = cur.next;
            //将cur的下一个节点指向新链表的头节点（最前端

            // 其实这里很像插入节点的情形
            // cur.next就是待插入的节点。
            // 待插入节点的next指向要插入位置的next
            // 这里一直是插入到第一个节点所以一直指向头节点的下一个节点（也就是第一个节点）
            // 需要先进行cur.next = reverseHead.next;是为了防止之前插入的节点丢失（不可达
            // 之后需要把头节点的下一个节点指向待插入节点即可完成插入
            cur.next = reverseHead.next;
            reverseHead.next = cur;
            cur = next; // 让cur后移

        }

        // 将head.next指向reverseHead.next,实现单链表的反转
        head.next = reverseHead.next;

    }

    // 查找单链表中的倒数第K个节点【新浪面试题】
    // 思路
    // 编写一共方法接收head节点，同时接收一个index
    // index表示是倒数第index个节点
    // 把链表从头到尾遍历，得到链表的总长度 getLength()
    // 得到size后，从链表的第一个开始遍历（size-index)个就可以得到
    public static HeroNode findLastIndexNode(HeroNode head,int index){
        // 判断如果链表为空，返回null
        if (head.next == null){
            return null;
        }

        // 第一个遍历得到链表长度（节点个数
        int size = getLength(head);
        // 第二次遍历size-index位置，就是我们要找的倒数第K个节点
        // 先做一个index的校验
        // 所有传过来的形参都需要做校验不管是判空还是超出范围
        if (index<=0 || index>size){
            return null;
        }

        // 定义辅助变量
        HeroNode cur = head.next;
        // for 循环定位到倒数的index
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }

        return cur;

    }



    // 方法：获取到单链表的节点个数（如果是带头节点的链表，需要不统计头节点)
    /**
     *
     * @param head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public static int getLength(HeroNode head){
        if (head.next == null){
            // 空链表
            return 0;
        }
        int length = 0;
        // 定义一共辅助变量
        // 同一去除头节点不统计头节点
        HeroNode cur = head.next;
        while (cur !=null){
            length++;
            cur = cur.next;// 遍历
        }
        return length;
    }

}







//定义SingleLinkedList管理
class SingleLinkedList {
    // 先初始化一个头节点，头节点不动，不存放具体数据
    private HeroNode head = new HeroNode(0,"","");


    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }

    // 添加节点到单向链表
    // 当不考虑编号顺序时
    // 1.找到当前链表的最后一个节点
    // 2.将最后这个节点的next指向新的节点
    public void add(HeroNode heroNode){
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode temp = head;

        // 遍历链表，找到最后
        while (true){
            // 找到链表的最后
            if (temp.next == null){
                break;
            }
            // 如果没有找到最后，将temp后移
            temp = temp.next;
        }

        // 当退出while，temp就指向了链表的最后
        // 将最后这个节点的next指向新的节点
        temp.next = heroNode;
    }
    // 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // （如果有这个排名则添加失败并给出提示）
    public void addByOrder(HeroNode heroNode){
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode temp = head;
        // 因为是单链表，因此我们找到的temp是位于添加位置的前一个节点
        // 单链表只能向后遍历无法向前所以不行
        boolean flag = false;// 标识添加的编号是否存在，默认false
        while (true){
            if (temp.next == null){
                // 说明temp已经在链表最后
                break;
            }

            // 如果这个no位于temp和temp的下一个节点之间
            // 因为temp一开始指向的是头节点
            // 所以使用next不会出现要插入的节点在第一个节点就无法插入的情况
            if(temp.next.no > heroNode.no){
                // 位置找到了
                break;
            }else if (temp.next.no == heroNode.no){
                // 编号存在
                flag = true;
                break;
            }
            temp = temp.next; // 后移
        }

        if (flag){
            System.out.printf("准备插入的英雄编号%d已经存在，不能加入\n",heroNode.no);
        }else {
            // 可以插入
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    // 修改节点的信息，根据no编号来修改，即no编号不能改
    // 说明
    // 1.根据newHeroNode的no来修改即可
    public void update(HeroNode newHeroNode){
        // 判断是否空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }

        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode temp = head;

        // 是否找到
        boolean flag = false;
        while (true){
            if (temp == null){
                break;// 已经遍历到最后
            }
            if (temp.no == newHeroNode.no){
                // 找到
                flag = true;
                break;
            }
            temp = temp.next;
        }

        // 根据flag判断是否找到
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        }else {
            System.out.println("找不到相同节点");
        }


    }

    // 删除节点
    // 思路
    // head节点不能动，因此需要一个temp辅助节点找到待删除节点的前一个节点
    // 说明我们在比较时，是temp.next.no 和 需要删除的节点的no比较
    public void del(int no){
        HeroNode temp = head;
        boolean flag = false; // 标识要删除节点
        while (true){
            if (temp.next==null){
                // 已经找到链表最后
                break;
            }
            if (temp.next.no == no){
                //找到了待删除结点的前一个节点temp
                flag = true;
                break;
            }


            temp = temp.next;
        }

        if (flag){
            // 如果flag为真说明找到了想要删除的节点
            // 删除节点
            temp.next = temp.next.next;
        }else {
            System.out.printf("要删除的节点%d不存在",no);
        }

    }

    // 显示链表（遍历
    public void list(){
        // 判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode temp = head.next;
        while (true){
            // 判断是否到链表最后
            if (temp == null){
                break;
            }
            // 输出节点信息
            System.out.println(temp);
            // 指向下一个节点
            temp = temp.next;
        }
    }
}

// 定义HeroNode,每个HeroNode 对象就是一个节点
class HeroNode{
    public int no;
    public String name;
    public String nickName;
    public HeroNode next; // 指向下一个节点

    // 构造器

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
