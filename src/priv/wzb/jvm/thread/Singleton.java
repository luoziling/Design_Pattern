package priv.wzb.jvm.thread;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-27 17:33
 * @description:
 **/

public class Singleton {
	private volatile static Singleton object;

	public static Singleton getInstance(){
		if (object == null){
			synchronized (Singleton.class){
				if (object == null){
					object = new Singleton();
				}
			}
		}
		return object;
	}

	public static void main(String[] args) {
		Singleton.getInstance();
	}
}
