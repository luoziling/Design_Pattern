package priv.wzb.design_pattern.createdpattern.protoyupepattern.example;

/**
 * @author Satsuki
 * @time 2020/2/3 14:37
 * @description:
 * 客户端测试类
 */
public class Client {
    public static void main(String[] args) {
        WeeklyLog logPrevious,logNew;
        // 创建原型对象
        logPrevious = new WeeklyLog();
        // 创建附件对象
        Attachment attachment = new Attachment();
        // 将附件添加到周报
        logPrevious.setAttachment(attachment);
        try {
            // 调用克隆方法创建克隆对象
            logNew = (WeeklyLog) logPrevious.clone();
            //比较周报
            System.out.println("周报是否相同？ " + (logPrevious==logNew));
            // 比较附件
            System.out.println("附件是否相同？" + (logPrevious.getAttachment()==logNew.getAttachment()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
