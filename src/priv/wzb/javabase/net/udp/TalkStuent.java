package priv.wzb.javabase.net.udp;

/**
 * @author Satsuki
 * @time 2019/9/8 20:37
 * @description:
 * 加入多线程，实现双向交流 模拟在线咨询
 */
public class TalkStuent {
    public static void main(String[] args) {
        new Thread(new TalkSend(7777,"localhost",9999)).start();
        new Thread(new TalkReceive(8888)).start();//接收
    }
}
