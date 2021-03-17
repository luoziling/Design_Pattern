package priv.wzb.javabook.streams;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-10-02 11:02
 * @description:
 **/

public class FileToWords {
	public static Stream<String> stream(String filePath)
			throws Exception {
		return Files.lines(Paths.get(filePath))
				.skip(1) // First (comment) line
				.flatMap(line ->
						Pattern.compile("\\W+").splitAsStream(line));
	}
}
