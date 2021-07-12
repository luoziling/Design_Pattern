package priv.wzb.design_pattern.behavioralpattern.visitorpattern.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-28 16:03
 * @description:
 **/

public class Main {
	public static void main(String[] args) throws IOException {
//		Files.walkFileTree(Paths.get("."),new MyFileVisitor());
		List<Integer> arrList = new ArrayList<>();
		arrList.add(1);
		Integer[] array = (Integer[])arrList.toArray(new Integer[0]);
		System.out.println("array = " + array);
		for (Integer integer : array) {
			System.out.println("integer = " + integer);
		}
	}
}
