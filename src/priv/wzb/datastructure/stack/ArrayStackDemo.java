package priv.wzb.datastructure.stack;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/10/29 21:41
 * @description:
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        // 测试一下ArrayStack 是否正确
        // 先创建一个ArrayStack对象->表示栈
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true; // 控制是否推出菜单
        Scanner scanner = new Scanner(System.in);

//        stack.push(10);
//        stack.push(20);
//        stack.push(30);
//        stack.push(40);
//        System.out.println("maxSize:"+stack.getMaxSize());
//        System.out.println("top:"+stack.getTop());
//        System.out.println("pop:"+stack.pop());
//        System.out.println("pop:"+stack.pop());
//        System.out.println("pop:"+stack.pop());
//        System.out.println("pop:"+stack.pop());
//        System.out.println("pop:"+stack.pop());
//        loop = false;

        while (loop){
            System.out.println("s: 表示显示栈");
            System.out.println("e: 退出程序");
            System.out.println("push: 表示添加数据到栈（入栈）");
            System.out.println("pop: 表示从栈取数据（出栈）");
            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch (key){
                case "s":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个值");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是%d\n",res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

// 定义一个ArrayStack 表示栈
class ArrayStack{
    private int maxSize; // 栈的大小
    private int[] stack; // 数组，数组模拟栈，数据就放在该数组中
    private int top = -1; //top表设计栈顶，初始化为-1

    //构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 栈满
    public boolean isFull(){
        return top == maxSize-1;
    }

    // 栈空
    public boolean isEmpty(){
        return top == -1;
    }

    //入栈-push
    public void push(int value){
        // 先判断是否栈满
        if (isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈,将栈顶数据返回
    public int pop(){
        // 判断栈是否为空
        if (isEmpty()){
            // 抛出异常
            throw new RuntimeException("栈空，没有数据");
        }
        int value = stack[top];
        top-- ;
        return value;
    }

    // 遍历显示
    public void list(){
        // 遍历时需要从栈顶开始显示数据
        if (isEmpty()){
            System.out.println("栈空，没有数据");
            return;
        }
        System.out.println("top:" + top);
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int[] getStack() {
        return stack;
    }

    public int getTop() {
        return top;
    }
}