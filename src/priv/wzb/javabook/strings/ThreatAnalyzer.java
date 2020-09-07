package priv.wzb.javabook.strings;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 17:32
 * @description: 正则表达式分析数据
 **/

public class ThreatAnalyzer {
    static String threatData =
            "58.27.82.161@08/10/2015\n" +
                    "204.45.234.40@08/11/2015\n" +
                    "58.27.82.161@08/11/2015\n" +
                    "58.27.82.161@08/12/2015\n" +
                    "58.27.82.161@08/12/2015\n" +
                    "[Next log section with different data format]";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(threatData);
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" +
                "(\\d{2}/\\d{2}/\\d{4})";
//        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" +
////                "(\\d{2}/\\d{2}/\\d{4})";
        while (scanner.hasNext(pattern)){
            scanner.next(pattern);
            MatchResult match = scanner.match();
            String ip = match.group(1);
            String date = match.group(2);
            System.out.format(
                    "Threat on %s from %s%n", date,ip);
        }
    }
}
