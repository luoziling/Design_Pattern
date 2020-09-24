package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 16:52
 * @description:
 **/

public class StaticIDField implements HasID {
	private static int count = 0;
	private int id = count++;
	@Override
	public int getID() {
		return id;
	}
}
