package priv.wzb.datastructure.linkedlist;

import java.util.LinkedList;

/**
 * @author Satsuki
 * @time 2019/10/29 20:17
 * @description:
 * Node<E> next;
 * Node<E> prev;
 * java.util.LinkedList也是由双向链表实现的
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
//        LinkedList
        // 进行测试
        // 先创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        // 创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
//        doubleLinkedList.add(hero1);
//        doubleLinkedList.add(hero2);
//        doubleLinkedList.add(hero3);
//        doubleLinkedList.add(hero4);

        // 按序插入
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);

        doubleLinkedList.list();

        // 修改
        HeroNode2 hero5 = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(hero5);
        System.out.println("修改后的链表情况：");
        doubleLinkedList.list();

        // 删除
        doubleLinkedList.del(3);
        System.out.println("删除后的链表情况");
        doubleLinkedList.list();



    }
}

// 创建一个双向链表的类
class DoubleLinkedList {
    // 先初始化一个头节点，头节点不要动，不存放具体的数据
    private HeroNode2 head = new HeroNode2(0,"","");

    // 返回头节点

    public HeroNode2 getHead() {
        return head;
    }

    // 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // （如果有这个排名则添加失败并给出提示）
    public void addByOrder(HeroNode2 heroNode){
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode2 temp = head;
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
            // 双向链表修改
            heroNode.next = temp.next;
            temp.next = heroNode;
            temp.next.pre = heroNode;
            heroNode.pre = temp;
        }
    }

    // 删除节点
    // 思路
    // 对于双向链表，我们可以直接找到要删除的节点
    // 找到后直接删除即可
    public void del(int no){
        // 判空
        if (head.next ==null){
            // 空链表不删除
            System.out.println("链表为空，无法删除");
            return;
        }
//        HeroNode2 temp = head;
        // 将temp指向待删除的节点即可，不用指向待删除节点的前一个节点
        HeroNode2 temp = head.next;
        boolean flag = false; // 标识要删除节点
        while (true){
            if (temp==null){
                // 已经找到链表最后
                break;
            }
            if (temp.no == no){
                //找到了待删除结点的前一个节点temp
                flag = true;
                break;
            }


            temp = temp.next;
        }

        if (flag){
            // 如果flag为真说明找到了想要删除的节点
            // 删除节点
//            temp.next = temp.next.next;
            // 双向链表的删除
            // 就是使待删除的节点从双向链表中剥离
            // 具体做法如下
            temp.pre.next = temp.next;
            // 代码有问题，考虑极端情况
            // 如果是最后一个节点就不需要执行下面这句话
            // 否则会出现空指针异常
            // 如果temp.next为null
            // null.pre会出错
            if (temp.next!=null){
                temp.next.pre = temp.pre;
            }

        }else {
            System.out.printf("要删除的节点%d不存在",no);
        }

    }


    // 可以看到双向链表的节点内容修改和单向链表几乎一样
    // 只是将原来的单向链表改为了双向链表
    // 修改节点的信息，根据no编号来修改，即no编号不能改
    // 说明
    // 1.根据newHeroNode的no来修改即可
    public void update(HeroNode2 newHeroNode){
        // 判断是否空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }

        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode2 temp = head;

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


    // 添加节点到双向链表
    // 当不考虑编号顺序时
    // 1.找到当前链表的最后一个节点
    // 2.将最后这个节点的next指向新的节点
    public void add(HeroNode2 heroNode){
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode2 temp = head;

        // 遍历链表，找到最后
        while (true){
            // 找到链表的最后
            if (temp.next == null){
                break;
            }
            // 如果没有找到最后，将temp后移
            temp = temp.next;
        }

//         当退出while，temp就指向了链表的最后
//        // 将最后这个节点的next指向新的节点
//        temp.next = heroNode;
        // 形成一个双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }



    // 遍历双向链表的方法
    // 显示链表（遍历
    public void list(){
        // 判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        // 因为head节点不能动所有用一个temp代替（辅助遍历
        HeroNode2 temp = head.next;
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



// 定义HeroNode2,每个HeroNode 对象就是一个节点
class HeroNode2{
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next; // 指向下一个节点
    public HeroNode2 pre;  // 指向前一个节点


    // 构造器

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
