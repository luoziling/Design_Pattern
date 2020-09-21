package priv.wzb.javabook.atunit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-17 11:08
 * @description:
 **/

public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) throws Exception {
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true);
        new ProcessFiles(new AtUnit(),"class").start(args);
        if (failures == 0){
            System.out.println("OK (" + testsRun + " tests)");
        }else {
            System.out.println("(" + testsRun + " tests)");
            System.out.println(
                    "\n>>> " + failures + " FAILURE" +
                            (failures > 1 ? "S" : "") + " <<<");
            for(String failed : failedTests)
                System.out.println(" " + failed);
        }
    }
    @Override
    public void process(File cFile) {
        try {
            String cName = ClassNameFinder.thisClass(Files.readAllBytes(cFile.toPath()));
            if(!cName.startsWith("public:"))
                return;
            cName = cName.split(":")[1];
            if(!cName.contains("."))
                return; // Ignore unpackaged classes
            testClass = Class.forName(cName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
//        TestMethods testMethods = new TestMethods();
//        Method creator = null;
//        Method cleanup = null;
//        for(Method m : testClass.getDeclaredMethods()) {
//            testMethods.addIfTestMethod(m);
//            if (creator == null)
//                creator = checkForCreatorMethod(m);
//            if (cleanup == null)
//                cleanup = checkForCleanupMethod(m);
//        }
    }
}
