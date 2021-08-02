package priv.wzb.jvm.class_structure;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-07-29 17:51
 * @description:
 **/

public class InternTest {
	public double test(){
		return 1.123124124;
	}
	public static void main(String[] args) {
		String str2 = new String("str")+new String("01");
		str2.intern();
		String str1 = "str01";
		System.out.println(str2==str1);
	}

}
