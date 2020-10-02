package priv.wzb.javabook.patterns;

import java.util.Arrays;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-27 17:44
 * @description:
 **/

public class CommandPattern {
	public static void main(String[] args) {
		List<Runnable> macro = Arrays.asList(
				()-> System.out.println("Hello "),
				()-> System.out.println("World! "),
				()-> System.out.println("I'm the command pattern!! ")
		);
		macro.forEach(Runnable::run);
	}
}
