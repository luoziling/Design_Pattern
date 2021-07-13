package priv.wzb.javabook.strings;

/**
 * @program: Design_Pattern
 * @author: wangzibai
 * @create: 2020-09-07 15:30
 * @description:
 **/

public class WitherStringBuilder {
    public String implicit(String[] fields) {
        String result = "";
        for(String field : fields) {
            result += field;
        }
        return result;
    }
    public String explicit(String[] fields) {
        StringBuilder result = new StringBuilder();
        for(String field : fields) {
            result.append(field);
        }
        return result.toString();
    }
}
