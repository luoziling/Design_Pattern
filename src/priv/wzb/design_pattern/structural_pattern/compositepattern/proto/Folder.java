package priv.wzb.design_pattern.structural_pattern.compositepattern.proto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/5/6 22:53
 * @description:
 */
public class Folder {
    private String name;
    //定义集合folderList,用于存储Folder类型的成员
    //ArrayList比较适合查找
    private List<Folder> folderList = new ArrayList<>();
    //定义集合imageList，用于存储ImageFile类型的成员
    private List<ImageFile> imageList = new ArrayList<ImageFile>();
    //定义集合textList，用于存储TextFile类型的成员
    private List<TextFile> textList = new ArrayList<TextFile>();

    public Folder(String name){
        this.name = name;
    }

    //增加新的Folder类型的成员
    public void addFolder(Folder f){
        folderList.add(f);
    }

    //增加新的ImageFile类型的成员
    public void addImageFile(ImageFile image) {
        imageList.add(image);
    }

    //增加新的TextFile类型的成员
    public void addTextFile(TextFile text) {
        textList.add(text);
    }

    //需提供三个不同的方法removeFolder()、removeImageFile()和removeTextFile()来删除成员，代码省略

//需提供三个不同的方法getChildFolder(int i)、getChildImageFile(int i)和getChildTextFile(int i)来获取成员，代码省略

    public void killVirus(){
        System.out.println("****对文件夹'" + name + "'进行杀毒"); //模拟杀毒
        //如果是Folder类型的成员，递归调用Folder的killVirus()方法
        for(Object obj : folderList){
            ((Folder)obj).killVirus();
        }
        //如果是ImageFile类型的成员，调用ImageFile的killVirus()方法
        for(Object obj : imageList) {
            ((ImageFile)obj).killVirus();
        }

//如果是TextFile类型的成员，调用TextFile的killVirus()方法
        for(Object obj : textList) {
            ((TextFile)obj).killVirus();
        }
    }



}
