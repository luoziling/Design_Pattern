package priv.wzb.datastructure.hashtab;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/11/1 16:05
 * @description:
 */
public class HashTabDemo {
    public static void main(String[] args) {
        // 创建哈希表
        HashTab hashTab = new HashTab(7);

        //写一个菜单
        String key = "";
        boolean loop = true; // 控制是否推出菜单
        Scanner scanner = new Scanner(System.in);
        int id;

        while (loop){
            System.out.println("add: 添加雇员");
            System.out.println("list: 遍历");
            System.out.println("f: 查找");
            System.out.println("e: 退出程序");

            key = scanner.next();
            switch (key){
                case "add":
                    System.out.println("输入id：");
                    id = scanner.nextInt();
                    System.out.println("enter name:");
                    String name = scanner.next();
                    // 创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "f":
                    System.out.println("输入查找的id：");
                    id = scanner.nextInt();
                    hashTab.findByEmpId(id);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "e":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出了");

    }
}

// 创建HashTab管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size; //表示共有多少条链表

    public HashTab(int size) {
        this.size = size;
        // 初始化
        empLinkedListArray = new EmpLinkedList[size];
//        this.empLinkedListArray = empLinkedListArray;
        //?
        // 初始化每个链表
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    // 添加雇员
    public void add(Emp emp){
        // 根据员工的id，得到该员工应该添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 使用取模法编写散列函数
    public int hashFun(int id){
        return id%size;
    }

    // 根据输入id查找雇员
    public void findByEmpId(int id){
        int index = hashFun(id);
        Emp emp = empLinkedListArray[index].findEmpById(id);
        if (emp != null){
            System.out.printf("找到雇员id=%d\t" + emp.toString(),id);
            System.out.println();
        }else {
            System.out.println("在哈希表中未找到雇员");
        }
    }

    // 遍历
    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }
}

//表示链表
class EmpLinkedList{
    // 头指针，指向第一个Emp，因此这个链表的head直接指向第一个雇员
    private Emp head; //默认null

    // 添加雇员到链表
    // 假定当添加雇员时 id是自增长，即id的分配总是从小到大
    // 因此我们将该雇员直接加入到本链表的最后接口
    public void add(Emp emp){
        // 如果是添加第一个雇员
        if (head == null){
            head = emp;
            return;
        }

        // 如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
        Emp curEmp = head;
        while (true){
            if (curEmp.next == null){
                // 遍历到链表最后
                break;
            }
            curEmp = curEmp.next;
        }
        // 退出时直接将emp加入链表
        curEmp.next = emp;

    }

    // 遍历
    public void list(int no){
        if (head== null){
            System.out.printf("list%d is null\n",no+1);
            return;
        }
        System.out.printf("now list%d:",no+1);
        Emp curEmp = head;// Auxiliary pointer
        while (true){

            System.out.print(curEmp + "\t");

            if (curEmp.next == null){
                break;
            }
            curEmp = curEmp.next;
        }
        System.out.println();
    }

    // 根据id查找雇员
    public Emp findEmpById(int id){
        // 判空
        if (head == null){
            System.out.println("list is null");
            return null;
        }
        // Auxiliary pointer
        Emp curEmp = head;
        while (true){
            if (curEmp.id == id){
                // 找到
                break;
            }
            if (curEmp.next == null){
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;
        }

        return curEmp;
    }
}

// 表示一个雇员
class Emp{
    public int id;
    public String name;
    public Emp next; // next默认为空

    public Emp() {
    }

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
