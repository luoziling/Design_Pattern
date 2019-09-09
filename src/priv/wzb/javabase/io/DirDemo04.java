package priv.wzb.javabase.io;

/**
 * @author Satsuki
 * @time 2019/9/4 17:10
 * @description:
 */
public class DirDemo04 {
    public static void main(String[] args) {
//        File dir = new File("G:/java_project_idea/Design_Pattern/src/priv/wzb/javabase/io/img/a/b");
//        boolean flag = dir.mkdirs();
//        System.out.println(flag);
        printTen(1);
    }

    public static void printTen(int n){
        if(n>10){
            return;
        }
        System.out.println(n);
        printTen(n+1);

    }
}
