package priv.wzb.javabook.rtti;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-10 18:33
 * @description:
 * class1.isAssignableFrom(class2) 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
 * 对象计数
 *
 **/

public class TypeCounter extends HashMap<Class<?>, Integer> {
    private Class<?> baseType;

    public TypeCounter(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void count(Object obj) {
        Class<?> type = obj.getClass();
        if (!baseType.isAssignableFrom(type)) {
            throw new RuntimeException(
                    obj + " incorrect type: " + type +
                            ", should be type or subtype of " + baseType);
        }
        countClass(type);
    }

    private void countClass(Class<?> type) {
        Integer quantity = get(type);
        put(type, quantity == null ? 1 : quantity + 1);
        Class<?> superClass = type.getSuperclass();
        if (superClass!=null &&
                baseType.isAssignableFrom(superClass)){
            countClass(superClass);
        }
    }

    @Override
    public String toString() {
        String result = entrySet().stream()
                .map(pair->
                        String.format("%s=%s",pair.getKey().getSimpleName(),pair.getValue()))
                .collect(Collectors.joining(", "));
        return "{"+result+"}";
    }
}
