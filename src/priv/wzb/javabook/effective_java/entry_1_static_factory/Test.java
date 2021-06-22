package priv.wzb.javabook.effective_java.entry_1_static_factory;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-02 19:26
 * @description:
 **/

public class Test {
	public static void main(String[] args) {
		BigInteger bigInteger = BigInteger.probablePrime(4, new Random());
		System.out.println("bigInteger = " + bigInteger);
		Instant instant = Instant.now();
		Date d = Date.from(instant);
//		EnumSet.of()
		BigInteger bigInteger1 = BigInteger.valueOf(Integer.MAX_VALUE);
//		Array.newInstance()
		// 处于不同类中
//		Files.getFileStore()
	}
}
