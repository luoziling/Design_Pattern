package priv.wzb.javabase.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Satsuki
 * @time 2019/9/5 16:54
 * @description:
 * 转换流
 * InputStreamReader和OutputStreamWriter
 */
public class ConvertTest02 {
    public static void main(String[] args) {
        //操作System.in和System.out
//        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            //循环获取键盘输入，输出此内容
            String msg = "";
            while (!msg.equals("exit")){
                msg = reader.readLine();//循环读取
                writer.write(msg);//循环写出
                writer.newLine();
                writer.flush();//强制刷新
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
