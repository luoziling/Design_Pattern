package priv.wzb.javabook.patterns.shapes;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 11:56
 * @description:
 **/

public class Shape {
	private static int counter = 0;
	private int id = counter++;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + id +"]";
	}
	public void draw() {
		System.out.println(this + " draw");
	}
	public void erase() {
		System.out.println(this + " erase");
	}
}
