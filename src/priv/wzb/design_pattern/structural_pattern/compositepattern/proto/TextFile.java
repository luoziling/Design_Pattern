package priv.wzb.design_pattern.structural_pattern.compositepattern.proto;

/**
 * @author Satsuki
 * @time 2019/5/6 22:53
 * @description:
 */
public class TextFile {
    private String name;
    public TextFile(String name){
        this.name = name;
    }

    public void killVirus(){
        //简化代码，模拟杀毒
        System.out.println("----对文本文件'" + name + "'进行杀毒");
    }
}
