package priv.wzb.multi_threaded_high_concurrent.volatile1;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-28 10:48
 * @description:
 **/

public class ReOrderDemo {
	int a = 0;
	boolean flag = false;

	public void write() {
		a = 1;
		flag = true;
	}

	public void read() {
		if (flag){
			a = a + 5;
			System.out.println(a);
		}
	}

	public static void main(String[] args) {
		ReOrderDemo reOrderDemo = new ReOrderDemo();
		new Thread(reOrderDemo::write).start();
		new Thread(reOrderDemo::read).start();
	}
}
