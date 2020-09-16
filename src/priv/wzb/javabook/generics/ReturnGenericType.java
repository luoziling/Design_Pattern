package priv.wzb.javabook.generics;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-15 18:51
 * @description:
 **/

public class ReturnGenericType<T extends HasF> {
    private T obj;

    public ReturnGenericType(T obj) {
        this.obj = obj;
    }

    public T get(){
        // 这里返回具体类而不是基类，这有点用
        // 比如list 和linkedList
        // 如果返回list还需要向下cast转型
        // 使用T extends List返回T就可以直接返回子类LinkedList
        return obj;
    }
}
