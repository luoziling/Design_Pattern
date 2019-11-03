package priv.wzb.javabase.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Satsuki
 * @time 2019/10/8 22:56
 * @description:
 */
public class Test {

    /**
     * 测试捕获组
     */
    @org.junit.Test
    public void test01(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";

        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建Pattern对象
        Pattern r = Pattern.compile(pattern);

        // 创建matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }

    @org.junit.Test
    public void test02(){
        String str = "成都市(成华区)(武侯区)(高新区)";
        //
        /**
         * .*?
         * .表示匹配除"\r\n"之外的任何单个字符。
         * 若要匹配包括"\r\n"在内的任意字符，
         * 请使用诸如"[\s\S]"之类的模式。
         * *表示匹配依次或多次前面的字符
         * 正则表达式中 .* 与 .*? 的区别
         * 简单说是贪婪匹配与非贪婪匹配的区别。
         * 捕获组是把多个字符当一个单独单元进行处理的方法，它通过对括号内的字符分组来创建。
         * ()就是一个捕获组
         * (?=是一个非捕获匹配
         * \\在java中表示一个\也就是转义字符
         * \\(表示转义一个(
         * .?(?=\\()的意思就是先匹配任意个非\r\n（非制表符或换行符）的字符
         * 然后(?=后面转义了一个(就代表匹配多个字符但是必须在(之前
         *
         */
        Pattern p = Pattern.compile(".*?(?=\\()");
        Matcher m = p.matcher(str);
        if (m.find()){
            System.out.println(m.group());
        }
    }
}
