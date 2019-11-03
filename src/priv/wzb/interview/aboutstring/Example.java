package priv.wzb.interview.aboutstring;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author Satsuki
 * @time 2019/6/27 10:15
 * @description:
 */
public class Example {
    String str=new String("good");
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
    public String encodingTest(String str){
        String tempStr = "";
        try {
            tempStr = new String(str.getBytes("ISO-8859-1"),"GBK");
            tempStr = tempStr.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tempStr;
    }

    @Test
    public void testBase(){
        System.out.println((4.0-3.6)==0.40000001);
    }
}
