package priv.wzb.design_pattern.structural_pattern.compositepattern.nowachieve;

/**
 * @author Satsuki
 * @time 2019/5/6 23:14
 * @description:文本文件类：叶子构件
 */
public class TextFile extends AbstractFile {
    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    public void add(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    public void remove(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    public AbstractFile getChild(int i) {
        System.out.println("对不起，不支持该方法！");
        return null;
    }

    public void killVirus() {
        //模拟杀毒
        System.out.println("----对文本文件'" + name + "'进行杀毒");
    }
}
