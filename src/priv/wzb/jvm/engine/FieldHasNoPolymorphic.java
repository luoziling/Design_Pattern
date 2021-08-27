package priv.wzb.jvm.engine;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-18 18:08
 * @description: 字段没有多态
 **/

public class FieldHasNoPolymorphic {
	static class Father {
		public int money = 1;
		public Father() {
			money = 2;
			showMeTheMoney();
		}
		public void showMeTheMoney() {
			System.out.println("I am Father, i have $" + money);
		}
	}
	static class Son extends Father {
		public int money = 3;
		public Son() {
//			money = 4;
			showMeTheMoney();
		}
		public void showMeTheMoney() {
			System.out.println("I am Son, i have $" + money);
		}
	}
	public static void main(String[] args) {
		// I am Son, i have $0
		//I am Son, i have $4
		//This gay has $2
		Father gay = new Son();
		System.out.println("This gay has $" + gay.money);
	}
}
