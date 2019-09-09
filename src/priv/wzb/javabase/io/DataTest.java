package priv.wzb.javabase.io;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/5 17:13
 * @description:
 * 数据流
 * 写出后读取
 * 读取顺序保持一致
 * DataOutputStream
 * DataInputStream
 */
public class DataTest {
    public static void main(String[] args) throws IOException {
        //写出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        //操作数据类型+数据
        dos.writeUTF("编码辛酸泪");
        dos.writeInt(10);
        dos.writeBoolean(false);
        dos.writeChar('a');
        dos.flush();


        byte[] datas = baos.toByteArray();

        //读取
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(datas));
        
        //顺序与写出保持一致
        String msg = dis.readUTF();
        int age = dis.readInt();
        boolean flag = dis.readBoolean();
        char ch = dis.readChar();
        System.out.println(flag);
    }
}
