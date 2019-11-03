package priv.wzb.sort;

import org.junit.Test;

/**
 * @author Satsuki
 * @time 2019/10/24 13:57
 * @description:
 */
public class ThreeMetaTest {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;

        // 1>2?  结果：不大于，此时执行b++并且赋值给c，
        // 那么此时c就是2，b++过后就是3 a并没有执行++操作
        // 在java中三目运算符没有短路的概念
        // 判断到哪一步就运行:前或者后的表达式
        int c = a>b?a++:b++;

        System.out.println(c);
        System.out.println(a);
        System.out.println(b);

        /**
         * 运行结果
         * 2
         * 1
         * 3
         */


    }

    @Test
    public void test(){
//        boolean a = true;
        int a = 1;
        int b = 0;
        int c = 1;
//        System.out.println(a&&b++||c++);// 无法比较
    }
}
