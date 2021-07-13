package priv.wzb.javabook.patterns.shapes;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 15:00
 * @description:
 **/
interface PolymorphicFactory{
	Shape create();
}

/**
 * Supplier 无参数，任意返回值
 * 构造器就是这样的，所以可以传递构造器
 * 由于实现-Supplier<Shape>所以构造器可以接收supplier类型的函数
 */
class RandomShapes implements Supplier<Shape>{
	private final PolymorphicFactory[] factories;
	private Random rand = new Random(42);

	public RandomShapes(PolymorphicFactory... factories) {
		this.factories = factories;
	}

	@Override
	public Shape get() {
		return factories[rand.nextInt(factories.length)].create();
	}
}
public class ShapeFactory3 {
	public static void main(String[] args) {
		RandomShapes rs = new RandomShapes(
				Circle::new,
				Square::new,
				Triangle::new
		);
		long count = Stream.generate(rs)
				.limit(6)
				.peek(Shape::draw)
				.peek(Shape::erase)
				.count();
		System.out.println("count = " + count);
	}
}
