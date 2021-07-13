package priv.wzb.jvm.gc;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-11-10 20:00
 * @description:
 * 对象可以在被GC时自我拯救。
 * 这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 **/

public class FinalizeEscapeGC {
	// 一个静态对象，代表GC Roots
	public static FinalizeEscapeGC SAVE_HOOK = null;

	public void isAlive(){
		System.out.println("yes, i am still alive :)");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed!");
		// 自救，指向静态变量，恢复与GC Roots的连接
		FinalizeEscapeGC.SAVE_HOOK = this;
	}

	public static void main(String[] args) throws InterruptedException {
		SAVE_HOOK = new FinalizeEscapeGC();
		// 对象第一次成功拯救自己
		SAVE_HOOK = null;
		System.gc();
		// 因为Finalizer方法优先级低，暂停确保方法执行
		Thread.sleep(500);
		if (SAVE_HOOK != null){
			SAVE_HOOK.isAlive();
		}else {
			System.out.println("no, i am dead :(");
		}

		// 再次自救就失败了
		SAVE_HOOK = null;
		System.gc();
		// 因为Finalizer方法优先级低，暂停确保方法执行
		Thread.sleep(500);
		if (SAVE_HOOK != null){
			SAVE_HOOK.isAlive();
		}else {
			System.out.println("no, i am dead :(");
		}
	}
}
