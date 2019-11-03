package priv.wzb.javabase.DynamicCompile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @author Satsuki
 * @time 2019/9/12 16:58
 * @description:
 */
public class Demo01 {
    public static void main(String[] args) {
        //通过IO流，将字符串存储为一个零时文件然后调用动态编译方法。
        String str = "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null,null,null,"G:\\java_project_idea\\Design_Pattern\\src\\priv\\wzb\\javabase\\DynamicCompile\\HelloWorld.java");
        System.out.println(result==0?"编译成功":"编译失败");
    }
}
