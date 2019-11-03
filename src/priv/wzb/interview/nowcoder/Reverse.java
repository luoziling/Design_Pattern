package priv.wzb.interview.nowcoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/10/26 21:39
 * @description:
 */
public class Reverse {
    public static String reverse(String str){
        if (str!=null){
            List<String> strList = new ArrayList<>();
            List<String> strList1 = new ArrayList<>();
            String[] s = str.split(" ");
            for(String a: s){
                strList.add(a);
            }
            if (strList.size()>0){
                for (int i = strList.size()-1; i >=0 ; i--) {
                    System.out.printf(strList.get(i) + " ");
                    strList1.add(strList.get(i) + " ");
                }
                StringBuilder sb = new StringBuilder();
                for(String s1:strList1){
                    sb.append(s1);
                }
                return sb.toString().trim();
            }

        }
        return "";
    }

    public static void main(String[] args) {
        String reverse = reverse("i am a boy!");
        System.out.println("reverse:" + reverse);
    }
}
