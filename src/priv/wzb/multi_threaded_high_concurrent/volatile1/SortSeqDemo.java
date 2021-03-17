package priv.wzb.multi_threaded_high_concurrent.volatile1;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-28 10:54
 * @description:
 **/

public class SortSeqDemo {
	int a = 0;
	boolean flag = false;

	public void method01() {
		a = 1;
		flag = true;
	}

	public void method02() {
		if (flag) {
			a = a+5;
			System.out.println(a);
		}
	}

	public static void main(String[] args) {
		SortSeqDemo sortSeqDemo = new SortSeqDemo();
		new Thread(sortSeqDemo::method01).start();
		new Thread(sortSeqDemo::method02).start();
//		sortSeqDemo.method01();
//		sortSeqDemo.method02();
	}
}
