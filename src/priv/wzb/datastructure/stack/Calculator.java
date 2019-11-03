package priv.wzb.datastructure.stack;

/**
 * @author Satsuki
 * @time 2019/10/29 22:18
 * @description:
 * 表达式运算
 */
public class Calculator {
    public static void main(String[] args) {
        // 根据前面的思路，完成表达式的运算
//        String expression = "3+2*6-2";
        String expression = "70+20*6-4";
        // 创建两个栈，树栈，一个符号栈
        ArrayStac2 numStack = new ArrayStac2(10);
        ArrayStac2 operStack = new ArrayStac2(10);
        // 定义需要的相关变量
        int index = 0; // 用于扫描
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' '; //将每次扫描得到的char保存到ch
        String keepNumber = "";
        // 开始while循环的扫描expression
        while (true){
            //依次得到expression的每一个字符
            ch = expression.substring(index,index+1).charAt(0);
            // 判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)){
                // 如果是运算符
                // 判断当前的符号栈是否为空
                if (!operStack.isEmpty()){

                    // 如果不为空
                    // 如果符号栈有操作符，就进行比较，如果当前的操作符优先级小于等于栈中的操作符，就需要pop两个数
                    // 再从符号栈中pop一个符号，进行运算，将得到的结果入数栈，然后将当前的操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())){
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1,num2,oper);
                        // 把运算结果入数栈
                        numStack.push(res);
                        //然后把当前操作符入符号栈
                        operStack.push(ch);
                    }else {
                        // 如果当前的操作符优先级大于栈中的操作符，就直接入符号栈
                        operStack.push(ch);// 1 + 3
                    }
                }else {
                    //如果为空直接入符号栈
                    // 如果为空直接入栈。。
                    operStack.push(ch);
                }
            }else {
                // 如果是数，则直接入数栈
                //1. 当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多位数
                //2. 在处理数，需要向expression的表达式的index 后再看一位,如果是数就进行扫描，如果是符号才入栈
                //3. 因此我们需要定义一个变量 字符串，用于拼接

                // 处理多位数
                keepNumber += ch;

                // 如果ch已经是expression的最后一位就直接入栈
                if (index == expression.length()-1){
                    numStack.push(Integer.parseInt(keepNumber));
                }else {
                    // 判断下一个字符是不是数字，如果是数字就继续扫描，如果是运算符，则入栈
                    // 注意是看后一位，不是index++
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))){
                        // 如果后一位是运算符，则入栈
                        numStack.push(Integer.parseInt(keepNumber));
                        // 重要的！！！！！！！keepNumber清空
                        keepNumber = "";
                    }
                }


//                numStack.push(ch-48);
//                numStack.push(ch-'0');
            }
            // 让index+1,并判断是否扫描到expression最后
            index ++;
            if (index >= expression.length()){
                break;
            }
        }
        // 当表达式扫描完毕，就顺序从数栈和符号栈中pop出相应的数和符号，并运行
        while (true){
            // 如果符号栈为空，则计算到最后结果，数栈中只有一个数字（结果）
            if (operStack.isEmpty()){
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1,num2,oper);
            numStack.push(res);
        }

        int res2 = numStack.pop();
        System.out.printf("表达式%s = %d",expression,res2);
    }
}

// 定义一个ArrayStack 表示栈
class ArrayStac2{
    private int maxSize; // 栈的大小
    private int[] stack; // 数组，数组模拟栈，数据就放在该数组中
    private int top = -1; //top表设计栈顶，初始化为-1

    //构造器
    public ArrayStac2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 增加一个方法，可以返回当前栈顶的值，但是不是真正的pop
    public int peek(){
        return stack[top];
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

    // 返回运算符的优先级，优先级是程序员来确定的。
    // 优先级使用数字表示，数字越大，优先级越高。
    public int priority(int oper){
        if (oper == '*' || oper == '/'){
            return 1;
        }else if (oper == '+' || oper == '-'){
            return 0;
        }else {
            return -1; // 假定目前的表达式只有加减乘除，连小括号都没有
        }
    }

    //判断是不是一个运算符
    public boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/' ;
    }

    // oper存入符号栈之后因为栈用的是int数组存储所以char类型的符号会根据ASCII码转为int
    // 所以这样判断不会出错
    // 计算方法
    public int cal(int num1,int num2,int oper){
        int res = 0; // res用于存放运算结果
        switch (oper){
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2/num1;
                break;
        }
        return res;
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
