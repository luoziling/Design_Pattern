package priv.wzb.javabook.patterns.shapes;

import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 14:39
 * @description:
 **/

public class FactoryTest {
	public static void test(FactoryMethod factory){
		Stream.of("Circle", "Square", "Triangle",
				"Square", "Circle", "Circle", "Triangle")
				.map(factory::create)
				.peek(Shape::draw)
				.peek(Shape::erase)
				.count();
	}
}
