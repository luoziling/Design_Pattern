package priv.wzb.jvm.classload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-11 10:40
 * @description:
 **/

public class ClassLoaderTest {
	public static void main(String[] args) throws Exception{
		ClassLoader classLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
					// 在寻找过程中有parent class loader就从父类加载器获取
					InputStream is = getClass().getResourceAsStream(fileName);
					if (Objects.isNull(is)){
						return super.loadClass(name);
					}
					byte[] bytes = new byte[is.available()];
					is.read(bytes);
					return defineClass(name,bytes,0,bytes.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		// 自定义类加载器加载类并生成实例对象
//		Object obj = classLoader.loadClass("priv.wzb.jvm.classload.ClassLoaderTest").newInstance();
		// class priv.wzb.jvm.classload.ClassLoaderTest 类文件没问题
//		System.out.println("obj.getClass() = " + obj.getClass());
		// 但是并非具体类实例 原因在于类加载器不同，导致类签名不一致
//		System.out.println(obj instanceof priv.wzb.jvm.classload.ClassLoaderTest);

	}
}
