package priv.wzb.javabook.patterns.shapes;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 11:55
 * @description:
 **/

public class BadShapeCreation extends RuntimeException {
	public BadShapeCreation(String message) {
		super(message);
	}
}
