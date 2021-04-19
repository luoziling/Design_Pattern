package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.charroom;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:32
 * @description:
 **/

public class MediatorPatternDemo {
	public static void main(String[] args) {
		User robert = new User("Robert");
		User john = new User("John");

		robert.sendMessage("Hi! John!");
		john.sendMessage("Hello! Robert!");
	}
}
