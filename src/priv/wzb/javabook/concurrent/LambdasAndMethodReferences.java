package priv.wzb.javabook.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-22 15:06
 * @description:
 **/
class NotRunnable{
	public void go(){
		System.out.println("NotRunnable");
	}
}
class NotCallable{
	public Integer get(){
		System.out.println("NotCallable");
		return 1;
	}
}
public class LambdasAndMethodReferences {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec =
				Executors.newCachedThreadPool();
		exec.submit(()-> System.out.println("labmda1"));
		exec.submit(new NotRunnable()::go);
		exec.submit(()->{
			System.out.println("lambda2");
			return 1;
		});
		exec.submit(new NotCallable()::get);
		exec.shutdown();
	}
}
