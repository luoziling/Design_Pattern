package priv.wzb.javabase.serializable;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Satsuki
 * @time 2019/10/18 20:46
 * @description:
 * 序列化测试
 * 序列化存储到本地，若从存储介质中读取数据字节流并反序列化的时候并不会调用类的构造函数
 */
public class WriteObject {

    @Test
    public void TestSer(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.txt"));
            Person person = new Person("Satsuki",23);
            oos.writeObject(person);
//            ConcurrentHashMap
//            HashMap

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AntiSer(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"));
            try {
                Person person = (Person) ois.readObject();
                System.out.println(person);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
