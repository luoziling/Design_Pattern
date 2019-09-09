package priv.wzb.design_pattern.structural_pattern.compositepattern.example;

/**
 * @author Satsuki
 * @time 2019/5/6 23:07
 * @description:
 */
public class Leaf extends Component {
    @Override
    public void add(Component c) {
        //异常处理或错误提示

    }

    @Override
    public void remove(Component c) {
        //异常处理或错误提示

    }

    @Override
    public Component getChild(int i) {
        //异常处理或错误提示
        return null;
    }

    @Override
    public void operation() {
        //叶子构件具体业务方法的实现

    }
}
