package priv.wzb.design_pattern.structural_pattern.compositepattern.nowachieve;

/**
 * @author Satsuki
 * @time 2019/5/6 23:12
 * @description:抽象文件类：抽象构件
 */
public abstract class AbstractFile {
    public abstract void add(AbstractFile file);
    public abstract void remove(AbstractFile file);
    public abstract AbstractFile getChild(int i);
    public abstract void killVirus();
}
