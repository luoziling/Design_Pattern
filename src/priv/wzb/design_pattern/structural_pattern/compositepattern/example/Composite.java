package priv.wzb.design_pattern.structural_pattern.compositepattern.example;

import java.util.ArrayList;

/**
 * @author Satsuki
 * @time 2019/5/6 23:08
 * @description:
 */
public class Composite extends Component {
    private ArrayList<Component> list = new ArrayList<Component>();

    public void add(Component c) {
        list.add(c);
    }

    public void remove(Component c) {
        list.remove(c);
    }

    public Component getChild(int i) {
        return (Component)list.get(i);
    }

    public void operation() {
        //容器构件具体业务方法的实现
        //递归调用成员构件的业务方法
        for(Object obj:list) {
            ((Component)obj).operation();
        }
    }
}
