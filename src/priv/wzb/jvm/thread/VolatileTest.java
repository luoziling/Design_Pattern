package priv.wzb.jvm.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-27 14:17
 * @description: volatiletest
 **/

public class VolatileTest {
	public volatile static int race = 0;
	public static void increase(){
		race++;
	}

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(20);
		// 开启20个线程，看看volatile能否保证并发操作的正确
		Thread[] threads = new Thread[20];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						increase();
					}
					latch.countDown();
				}
			});
			threads[i].start();
//			threads[i].join();
		}
		// 等待所有线程执行完毕
//		while (Thread.activeCount()>1){
//			Thread.yield();
//		}
		latch.await();

		System.out.println("race = " + race);
	}

//	@Test
//	public void testInstruction(){
//		Map configOptions;
//		char[] configText;
//		// 此变量必须定义为volatile
//		volatile boolean initialized = false;
//		// 假设以下代码在线程A中执行
//		// 模拟读取配置信息，当读取完成后
//		// 将initialized设置为true,通知其他线程配置可用
//		configOptions = new HashMap();
//		configText = readConfigFile(fileName);
//		processConfigOptions(configText, configOptions);
//		initialized = true;
//		// 假设以下代码在线程B中执行
//		// 等待initialized为true，代表线程A已经把配置信息初始化完成
//		while (!initialized) {
//			sleep();
//		}
//		// 使用线程A中初始化好的配置信息
//		doSomethingWithConfig();
//
//	}
}
