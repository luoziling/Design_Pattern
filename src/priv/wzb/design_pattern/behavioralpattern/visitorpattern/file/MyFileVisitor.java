package priv.wzb.design_pattern.behavioralpattern.visitorpattern.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-06-28 16:04
 * @description: 自定义文件访问者
 * NIO中对文件的访问分别是文件夹与普通文件
 **/

public class MyFileVisitor extends SimpleFileVisitor<Path> {
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		// 访问者具体访问方法，对某个元素的抽象做访问这里的元素抽象指path可能是文件夹/文件
		System.out.println("MyFileVisitor.preVisitDirectory visit Directory:" + dir);
		return super.preVisitDirectory(dir, attrs);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		System.out.println("MyFileVisitor.visitFile visit file:" + file);
		return super.visitFile(file, attrs);
	}
}
