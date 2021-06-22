package priv.wzb.interview.aboutstring;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/6/27 10:15
 * @description:
 */
public class Example {
    // string比较特殊，看似new是构建对象实际上是一个final的字符数组，
    // 由于其不可变性，导致传递的并非引用而是类似primitive的值传递
    String str=new String("good");
    // 数组是对象
    char [] ch={'a','b','c'};

    public static void main(String[] args) {
        Example ex=new Example();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str+"and");
        System.out.print(ex.ch);
    }

    public void change(String  str,char ch[]){
        str="test ok";
        ch[0]='g';
    }

    @Test
    public void encodingTest(){
        String tempStr = "";
        try {
            tempStr = new String(str.getBytes("ISO-8859-1"),"GBK");
            tempStr = tempStr.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("tempStr = " + tempStr);
//        return tempStr;
    }

    @Test
    public void testBase(){
        System.out.println((4.0-3.6)==0.40000000000000000000000000000000000001);
    }

    @Test
    public void stringHash(){
        String key1 = "a";
        System.out.println("key1.hashCode() = " + key1.hashCode());
        Map<String, Integer> map = new HashMap<>();
        map.put(key1, 123);

        String key2 = new String("a");
        System.out.println("key2.hashCode() = " + key2.hashCode());
        map.get(key2); // 123

        // 直接==是比较对象hash
        System.out.println(key1 == key2); // false
        System.out.println(key1.equals(key2)); // true
    }

    @Test
    public void primitiveAndObject(){
        int a = 1;
        Integer b = new Integer(1);
        System.out.println(a == b);
    }
}
