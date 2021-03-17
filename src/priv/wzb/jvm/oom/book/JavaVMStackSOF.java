package priv.wzb.jvm.oom.book;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-05 18:17
 * @description:
 * VM Args：-Xss128k
 *
 **/

public class JavaVMStackSOF {
	private int stackLength = 1;
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:" + oom.stackLength);
			throw e;
		}
	}
}
