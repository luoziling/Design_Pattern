package priv.wzb.design_pattern.createdpattern.protoyupepattern.example;

import java.io.*;

/**
 * @author Satsuki
 * @time 2020/2/3 14:34
 * @description:
 * 工作周报类，充当原型角色。在真实环境下该类将比较复杂，考虑到代码的可读性，
 * 再次只列出部分与模式相关的核心代码
 */
public class WeeklyLog implements Cloneable, Serializable {
    // 为了简化设计和实现，假设一份工作周报中只有一个附件对象
    // 在实际情况中可以包含多个附件，使用集合对象实现
    private Attachment attachment;
    private String name;
    private String date;
    private String content;

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 使用clone()方法实现浅克隆

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // 使用序列化计数实现深克隆
    public WeeklyLog deepClone() throws IOException,ClassNotFoundException,OptionalDataException{

        // 将对象写入流
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);
        // 将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (WeeklyLog)ois.readObject();
    }
}
