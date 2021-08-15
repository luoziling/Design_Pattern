package priv.wzb.javabase.jvm;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-03 13:40
 * @description:
 **/

public class Access {

	 public static abstract class SubAccess{
	 	int a = 123;long b = 8223372036854775807L;
		long c =  a+b;
		float d= 1.1f;double eDouble= 2.2;
		double a1 = d+eDouble;
	 }
}
