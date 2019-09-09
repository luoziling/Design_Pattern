package priv.wzb.javabase.io;

import java.io.*;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/9/5 17:13
 * @description:
 * 对象流
 * 写出后读取
 * 读取顺序保持一致
 * ObjectOutputStream
 * ObjectInputStream
 * 不是所有对象都可以序列化必须加上Serializable
 */
public class ObjectTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //写出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        //操作数据类型+数据
        oos.writeUTF("编码辛酸泪");
        oos.writeInt(10);
        oos.writeBoolean(false);
        oos.writeChar('a');
        //对象
        oos.writeObject("谁解其中味");
        oos.writeObject(new Date());
        Employee emp = new Employee("mayun",400);
        oos.writeObject(emp);
        oos.flush();


        byte[] datas = baos.toByteArray();

        //读取
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(datas));
        
        //顺序与写出保持一致
        String msg = ois.readUTF();
        int age = ois.readInt();
        boolean flag = ois.readBoolean();
        char ch = ois.readChar();
        System.out.println(flag);

        Object str = ois.readObject();
        Object date = ois.readObject();
        Object emp1= ois.readObject();

        if(str instanceof String){
            System.out.println((String)str);
        }

        if(date instanceof Date){
            System.out.println((Date)date);
        }
        if(emp1 instanceof Employee){
            System.out.println((Employee)emp1);
        }
    }
}

class Employee implements Serializable{
    //transient修饰的变量回避免被序列化
    private transient String name;
    private double salary;

    public Employee() {
    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
