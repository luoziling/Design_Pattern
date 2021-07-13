package priv.wzb.javabook.patterns;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 10:04
 * @description:
 * 单例模式
 **/
interface Resource{
	int getValue();
	void setValue(int x);
}
/**
 * 由于这不是从Cloneable基类继承而且没有添加可克隆性，
 * 因此将其设置为final可防止通过继承添加可克隆性。
 * 这也实现了线程安全的延迟初始化：
 */
final class Singleton{
	private static final class ResourceImpl implements Resource{
		private int i;

		/**
		 * 构造函数私有化避免直接访问
		 * @param i
		 */
		private ResourceImpl(int i) {
			this.i = i;
		}

		@Override
		public synchronized int getValue() {
			return i;
		}

		@Override
		public synchronized void setValue(int x) {
			i = x;
		}
	}
	private static class ResourceHolder{
//		static关键字 饿汉模式的实现，类加载时就初始化
		private static Resource resource = new ResourceImpl(47);
	}
	public static Resource getResource(){
		return ResourceHolder.resource;
	}
}
public class SingletonPattern {
	public static void main(String[] args) {
		Resource r = Singleton.getResource();
		System.out.println(r.getValue());
		Resource s2 = Singleton.getResource();
		// 指向同一个对象，所以值修改后对所有引用都可见
		s2.setValue(9);
		System.out.println(r.getValue());
//		try {
//			Singleton s3 = (Singleton) s2.clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
	}
}
