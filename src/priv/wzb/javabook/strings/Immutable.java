package priv.wzb.javabook.strings;

import org.junit.Test;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 15:07
 * @description: String的不可变
 **/

public class Immutable {
    public static String upcase(String s){
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        String q = "howdy";
        System.out.println(q);
        // 如果传的是对象的引用那么对对象的操作会导致对象改变
        // 但这里穿的是一个副本，只有在upcase操作的时候曹辉产生一个局部变量
        // 返回后局部变量消失，类似Primitive的值传递
        String qq = upcase(q);
        System.out.println(qq);
        System.out.println(q);
    }

    @Test
    public void testContains(){
        String s = "abcde";
        System.out.println(s.contains("abc"));
    }
}
