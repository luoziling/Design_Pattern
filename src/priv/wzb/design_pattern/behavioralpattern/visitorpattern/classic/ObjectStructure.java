package priv.wzb.design_pattern.behavioralpattern.visitorpattern.classic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Satsuki
 * @time 2019/6/9 19:40
 * @description:
 */
public class ObjectStructure {
    private ArrayList<Element> list = new ArrayList<>();//定义一个集合用于存储元素对象

    public void accept(Visitor visitor) {
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ((Element) i.next()).accept(visitor);//遍历访问集合中的每一个元素
        }
    }

    public void addElement(Element element) {
        list.add(element);
    }

    public void removeElement(Element element) {
        list.remove(element);
    }
}
