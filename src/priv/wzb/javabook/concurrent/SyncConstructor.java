package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 17:09
 * @description:
 **/

public class SyncConstructor implements HasID {
	private final int id;
	private static Object constructorLock =
			new Object();

	public SyncConstructor(int id) {
		synchronized (constructorLock){
			this.id = id;
		}
	}

	@Override
	public int getID() {
		return 0;
	}
}
