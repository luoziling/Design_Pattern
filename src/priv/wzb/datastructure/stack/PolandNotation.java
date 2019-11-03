package priv.wzb.datastructure.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * @author Satsuki
 * @time 2019/10/30 13:44
 * @description:
 * 中缀表达式转后缀表达式（逆波兰表达式）
 * 然后运算
 */
public class PolandNotation {
    public static void main(String[] args) {
        // 完成将一个中缀表达式转后缀表达式
        // 中缀表达式 1 + ( ( 2 + 3 )× 4) - 5 =》 后缀表达式
        // 将 s2 出栈  - 5 + * 4 + 3 2 1  =>  1 2 3 + 4 * + 5 -
        // 因为直接对str操作不方便，因此先将运算式转为中缀表达式对应的list
        // 将得到的中缀表达式对应的list转为后缀表达式对应的list

        String expression = "1+((2+3)*4)-5";
        List<String> list = toInFixExpressionList(expression);
        System.out.println("中缀表达式："+list);
        List<String> suffixExpressionList = parseSuffixExpressionList(list);

        // 后缀表达式：[1, 2, 3, +, 4, *, +, 5, -]
        System.out.println("后缀表达式：" + suffixExpressionList);

        System.out.printf("expression=%d",calculate(suffixExpressionList));




        /*

        // 先定义一个逆波兰表达式
        // (3+4)*5-6 => 3 4 + 5 * 6 -
        // 4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +
        // 为了方便，逆波兰表达式的数字和符号用空格隔开
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        // 思路
        // 先将suffixExpression放到ArrayList
        // 将ArrayList 传递给一个方法，配合栈完成计算
        List<String> list = getListString(suffixExpression);
        for (String s:list){
            System.out.println(s);
        }

        System.out.println("list:" + list);

        int res = calculate(list);
        System.out.println("res:" + res);

        */


    }

    // 方法：将得到的中缀表达式对应的list转为后缀表达式对应的list
    /**
     * 前缀 prefix
     * 中缀 infix
     * 后缀 suffix
     */
    public static List<String> parseSuffixExpressionList(List<String> ls){
        // 定义两个栈
        Stack<String> s1 = new Stack<>();// 符号栈
        // 因为S2这个栈在整个过程中没有pop操作
        // 并且还要逆序输出太过麻烦
        // 使用ArrayList
//        Stack<String> s2 = new Stack<>();// 符号栈
        List<String> s2 = new ArrayList<>(); // 存储中间结果

        // 遍历 ls
        for (String item: ls){
            // 如果是一个数，加入s2
            if (item.matches("\\d+")){
                s2.add(item);
            }else if (item.equals("(")){
                s1.push(item);
            }else if (item.equals(")")){
                //如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，
                // 直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                s1.pop(); // !!! 将‘（’弹出s1栈
            }else {
                // 当item的优先级小于等于s1栈顶运算符，将s1栈顶的运算符弹出并加入到s2中，
                // 再次转到(4.1)与s1中新的栈顶运算符相比较；
                // 我们缺少一个比较优先级高低的方法
                while (s1.size()!=0&& Operation.getValue(s1.peek())>=Operation.getValue(item)){
                    s2.add(s1.pop());
                }
                // 还需要将item压入栈
                s1.push(item);
            }
        }

        //将s1中剩余的运算符依次弹出并压入s2
        while (s1.size()!=0){
            s2.add(s1.pop());
        }

        // 因为存放到list中所以正常按序输出就是后缀表达式对应的list
        return s2;

    }

    // 将一个逆波兰表达式，依次将数据和运算符放到ArrayList中
    public static List<String> getListString (String suffixExpression){
        String[] split = suffixExpression.split(" ");
//        Stream<String> stream = Arrays.stream(split);
        List<String> list = Arrays.asList(split);
        return list;

    }

    // 方法:将中缀表达式转为对应的List
    public static List<String> toInFixExpressionList(String s){
        // 定义一个list存放中缀表达式的内容
        List<String> ls = new ArrayList<>();
        int i = 0;// 这是一个指针用于遍历中缀表达式字符串
        String str;// 对多位数的拼接
        char c; // 每遍历一个字符，就放入到C
        do {
            // 如果c是一个非数字 '0'=>48 '9'=>57
            if ((c=s.charAt(i))<'0'|| (c=s.charAt(i))>'9'){
                ls.add(""+c);
                i++; // i需要后移
            }else {
                //如果是一个数就需要考虑多位数
                str = ""; // 先将str置为""
                while (i<s.length() && (c=s.charAt(i)) >= 48 && (c=s.charAt(i))<=57){
                    str +=c;// 拼接
                    i++;
                }
                ls.add(str);
            }
        }while (i < s.length());

        return ls;
    }

    // 完成对逆波兰表达式的运算
    /**
     * 从左至右扫描，将3和4压入堆栈；
     * 遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
     * 将5入栈；
     * 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
     * 将6入栈；
     * 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     */
    public static int calculate(List<String> ls){
        // 创建栈，只需要一个栈即可
        Stack<String> stack = new Stack<>();
        // 遍历 ls
        for (String item : ls){
            // 使用正则表达式取数
            //\d
            //
            //数字字符匹配。等效于 [0-9]。
            // 在java中 \\等于一个斜杠\
            if (item.matches("\\d+")){// 匹配的是多位数
                // 入栈
                stack.push(item);
            }else {
                //pop出两个数，并运算
                // 都是后面取出的数去+-*/前面取出的数
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")){
                    res = num1+num2;
                }else if(item.equals("-")){
                    res = num1-num2;
                }else if(item.equals("*")){
                    res = num1*num2;
                }else if(item.equals("/")){
                    res = num1/num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                // 把res 入栈
                stack.push(res + "");
            }
        }
        // 最后留在stack中的数据就是运算结果
        return Integer.parseInt(stack.pop());
    }

}

// 编写一个类Operation 可以返回一个运算符对应的优先级
class Operation{
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    // 写一个方法，返回对应的优先级数字
    public static int getValue(String operation){
        int res = 0;
        switch (operation){
            case "+":
                res = ADD;
                break;
            case "-":
                res = SUB;
                break;
            case "*":
                res = MUL;
                break;
            case "/":
                res = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }

        return res;
    }
}
