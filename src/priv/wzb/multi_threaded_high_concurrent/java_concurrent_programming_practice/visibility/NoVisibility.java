package priv.wzb.multi_threaded_high_concurrent.java_concurrent_programming_practice.visibility;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-09-17 15:15
 * @description:
 **/

public class NoVisibility {
	private static boolean ready;
	private static int number;
	private static class ReaderThread extends Thread{
		@Override
		public void run() {
			while (!ready){
				Thread.yield();
			}
			System.out.println("number = " + number);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReaderThread readerThread = new ReaderThread();
		readerThread.start();
		number = 1;
		ready = true;

	}
}
