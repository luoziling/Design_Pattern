package priv.wzb.javabook.streams;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-02 11:00
 * @description:
 **/

public class Peeking {
	public static void main(String[] args) throws Exception {
		FileToWords.stream("D:\\Learning\\Design_Pattern\\test.txt")
				.limit(4)
				.map(w -> w + " ")
				.peek(System.out::print)
				.map(String::toUpperCase)
				.peek(System.out::print)
				.map(String::toLowerCase)
				.forEach(System.out::print);
	}
}

	//Well WELL well it IT it s S s so SO so
