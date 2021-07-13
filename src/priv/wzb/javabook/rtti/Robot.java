package priv.wzb.javabook.rtti;

import java.util.List;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-11 16:45
 * @description:
 **/

public interface Robot {
    String name();

    String model();

    List<Operation> operations();

    static void test(Robot r){
        if (r instanceof Null)
            System.out.println("[Null Robot]");
        System.out.println("Robot name: " + r.name());
        System.out.println("Robot model: " + r.model());
        for (Operation operation : r.operations()) {
            System.out.println(operation.description.get());
            operation.command.run();
        }
    }
}
