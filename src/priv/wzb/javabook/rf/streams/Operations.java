package priv.wzb.javabook.rf.streams;
import java.util.*;
/**
 * @author Satsuki
 * @time 2020/8/13 22:12
 * @description:
 */
public interface Operations {
     String s = null;
//     static void show(String str){
//         s = str;
//     }
static void show(String str){
    System.out.println(str);
}

static void runOps(Operations... ops){

}

     void execute();
}
