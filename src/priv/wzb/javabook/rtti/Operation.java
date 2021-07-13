package priv.wzb.javabook.rtti;

import java.util.function.Supplier;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-11 16:47
 * @description:
 **/

public class Operation {
    public final Supplier<String> description;
    public final Runnable command;

    public Operation(Supplier<String> descr, Runnable cmd) {
        description = descr;
        command = cmd;
    }
}
