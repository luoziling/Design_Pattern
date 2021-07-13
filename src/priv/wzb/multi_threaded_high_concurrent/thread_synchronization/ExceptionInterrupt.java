package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-07-02 10:36
 * @description:
 * 测试异常是否会打断锁
 **/

public class ExceptionInterrupt {
	public static void main(String[] args) throws InterruptedException {
		Object o = new Object();
		Thread t1 = new Thread(()->{
			synchronized (o){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				throw new RuntimeException("测试异常");
			}
		});
		Thread t2 = new Thread(()->{
			synchronized (o){
				System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
			}
		});
		t1.start();
		t1.join();
		t2.start();
		t2.join();
		System.out.println("end");
	}
}
