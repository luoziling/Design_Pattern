package priv.wzb.jvm.class_structure;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-07-23 12:32
 * @description:
 **/

public class TestClass {
	private int m;
	public int inc(){
		double c = 2.123145124123213;
		System.out.println("c = " + c);
		return m+1;
	}
	public int ase(){
		double c = 2.123145124123213;
		return (int) c;
	}
	public static void main(String[] args) {
		String baseStr = "baseStr";
		final String baseFinalStr = "baseStr";

		String str1 = "baseStr01";
		String str2 = "baseStr"+"01";
		String str3 = baseStr + "01";
		String str4 = baseFinalStr+"01";
		String str5 = new String("baseStr01").intern();
		String str6 = "base123145";
		String str7 = "12312";
		System.out.println("str6 = " + str6);
		System.out.println("str6+str7 = " + str6 + str7);
		double test = 1.1234;
		System.out.println("test = " + test + 0.1);

		long a = 1235667;
		System.out.println("a+test = " + a + test);

		System.out.println(str1 == str2);
		System.out.println(str1 == str3);
		System.out.println(str1 == str4);
		System.out.println(str1 == str5);
	}
}
