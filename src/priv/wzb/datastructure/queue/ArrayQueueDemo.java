package priv.wzb.datastructure.queue;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/10/28 21:13
 * @description:
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        // 测试一把
        // 创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
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
class ArrayQueue{
    private int maxSize; // 表示数组的最大容量
    private int front; // 队列头
    private int rear; // 队列尾
    private int[] arr; // 该数组用于存放数据，模拟队列

    // 创建队列的构造器


    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1; // 指向队列头部，分析出front指向队列头部的前一个位置
        rear = -1; // 指向队列尾部，直接指向队列尾部的具体数据即就是队列最后一个数据
    }

    // 判断队列是否满
    public boolean isFull(){
        return rear == maxSize-1;
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
        rear++; // 让rear 后移
        arr[rear] = n;
    }

    // 数据出队列
    public int getQueue(){
        // 判断队列是否空
        if (isEmpty()){
            throw new RuntimeException("队列空，不能取数据");
        }
        front++; // front后移
        return arr[front];
    }

    // 显示数据队列所有数据
    public void showQueue(){
        // 遍历
        if (isEmpty()){
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            // 格式化打印
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    // 显示队列的头数据，注意不是取数据
    public int headQueue(){
        // 判断
        if (isEmpty()){
            throw new RuntimeException("队列为空没有数据");
        }
        return arr[front+1];
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }
}
