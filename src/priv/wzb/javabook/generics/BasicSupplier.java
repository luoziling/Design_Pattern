package priv.wzb.javabook.generics;

import java.util.function.Supplier;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 17:57
 * @description:
 **/

public class BasicSupplier<T> implements Supplier<T> {
    private Class<T> type;

    public BasicSupplier(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Supplier<T> create(Class<T> type){
        return new BasicSupplier<>(type);
    }

    public static void main(String[] args) {
        new BasicSupplier<String>(String.class);
        BasicSupplier.create(String.class);
    }
}
