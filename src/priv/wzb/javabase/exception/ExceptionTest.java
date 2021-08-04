package priv.wzb.javabase.exception;

import org.junit.Test;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-02 19:53
 * @description:
 **/

public class ExceptionTest {
	@Test
	public void test(){
		try {
			System.out.println("ExceptionTest.test");
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("true = " + true);
		}
	}
}
