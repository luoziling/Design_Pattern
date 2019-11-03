package priv.wzb.datastructure.queue;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/10/28 21:13
 * @description:
 * 思路如下:
 * 1.  front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
 * front 的初始值 = 0
 * 2.  rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
 * rear 的初始值 = 0
 * 3. 当队列满时，条件是  (rear  + 1) % maxSize == front 【满】
 * 4. 对队列为空的条件， rear == front 空
 * 5. 当我们这样分析， 队列中有效的数据的个数   (rear + maxSize - front) % maxSize   // rear = 1 front = 0
 * 6. 我们就可以在原来的队列上修改得到，一个环形队列
 */
public class CircleQueueDemo {
    public static void main(String[] args) {
        // 测试一把
        System.out.println("测试数组模拟环形队列");
        // 创建一个环形队列
        // 设置4，其队列最大容量是3 因为做了个约定要空一个空间
        // 約定的位置是在動態變化的
        //
        CircleQueue arrayQueue = new CircleQueue(4);
        char key = ' ';// 接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        // 输出一个菜单
        while (loop){
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);// 接收一个字符
            switch (key){
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.println("取出的数据是：" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.println("队列头的数据是：" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

// 使用数组模拟队列-编写一个ArrayQueue类
class CircleQueue{
    private int maxSize; // 表示数组的最大容量
    //   front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
    //front 的初始值 = 0
    private int front; // 队列头
    //  rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
    //rear 的初始值 = 0
    private int rear; // 队列尾
    private int[] arr; // 该数组用于存放数据，模拟队列

    // 创建队列的构造器


    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull(){
        // 不带循环的队列
//        return rear == maxSize-1;
        // 因为空出了一个空间
        // 容量3 maxSize=4
        // rear指向队列最后一个元素的后一个位置
        return (rear+1)%maxSize==front;
    }

    // 判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int n){
        if (isFull()){
            System.out.println("队列满，不能加入数据");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;


        // rear不可能为3 因为两个元素的话添加第三个元素的时候此时rear的值从2变为3 会取模变为0（此时maxSize=3)
        rear = (rear+1) % maxSize; // 让rear 后移 考虑取模

    }

    // 数据出队列
    public int getQueue(){
        // 判断队列是否空
        if (isEmpty()){
            throw new RuntimeException("队列空，不能取数据");
        }
        // 这里需要分析出front是指向队列的第一个元素
        // 1.先把front对应的值保存到一个临时变量
        // 2.将front后移，考虑取模
        // 3.将临时保存的变量返回
        int value = arr[front];
        // 取模是为了防止下标越界
        front = (front+1) % maxSize;
        return value;
    }

    // 显示数据队列所有数据
    public void showQueue(){
        // 遍历
        if (isEmpty()){
            System.out.println("队列为空，没有数据");
            return;
        }
        // 思路：从front开始遍历，遍历多少个元素

        for (int i = front; i < front + size(); i++) {
            // 格式化打印
            // 取模防止超出下标
            System.out.printf("arr[%d]=%d\n",i% maxSize,arr[i % maxSize]);
//            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    // 求出当前队列有效数据个数，否则无法遍历
    public int size(){
        // rear指向的是最后一个元素的后一个位置，要这样做是为了取元素个数时候的方便
        // rear = 1
        // front = 0
        // maxSize = 3
        // 一定要加一个maxSize不然-front可能变为负数
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列的头数据，注意不是取数据
    public int headQueue(){
        // 判断
        if (isEmpty()){
            throw new RuntimeException("队列为空没有数据");
        }
        return arr[front];
    }

}
