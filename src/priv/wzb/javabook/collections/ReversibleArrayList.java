package priv.wzb.javabook.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-09-01 16:30
 **/

public class ReversibleArrayList<T> extends ArrayList<T> {
    ReversibleArrayList(Collection<T> c){
        super(c);
    }
    public Iterable<T> reversed(){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int current = size()-1;
                    @Override
                    public boolean hasNext(){
                        return current>-1;
                    }
                    @Override
                    public T next(){
                        return get(current--);
                    }
                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
