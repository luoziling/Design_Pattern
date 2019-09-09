package priv.wzb.design_pattern.structural_pattern.compositepattern.nowachieve;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/5/6 23:16
 * @description:
 */
public class Folder extends AbstractFile{

    //定义集合fileList,用于存储AbstractFile类型的成员
    private List<AbstractFile> fileList = new ArrayList<>();
    private String name;

    public Folder(String name){
        this.name = name;
    }

    @Override
    public void add(AbstractFile file) {
        fileList.add(file);

    }

    @Override
    public void remove(AbstractFile file) {
        fileList.remove(file);

    }

    @Override
    public AbstractFile getChild(int i) {
        return (AbstractFile)fileList.get(i);
    }

    @Override
    public void killVirus() {
        System.out.println("****对文件夹'" + name + "'进行杀毒");  //模拟杀毒

        //递归调用成员构件的killVirus()方法
        for(AbstractFile file : fileList){
            file.killVirus();
        }
        //递归调用成员构件的killVirus()方法
        for(Object obj : fileList){
            ((AbstractFile)obj).killVirus();
        }


    }
}
