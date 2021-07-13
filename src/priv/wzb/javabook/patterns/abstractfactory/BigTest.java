package priv.wzb.javabook.patterns.abstractfactory;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 16:09
 * @description:
 **/

public class BigTest {
	@Test
	public void divideTest(){
		for (int i= 0; i < 100; i++) {
			BigDecimal level = new BigDecimal(i).divide(new BigDecimal(10000));
			System.out.println(level);
		}
	}
}
