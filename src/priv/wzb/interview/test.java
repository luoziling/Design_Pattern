package priv.wzb.interview;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Satsuki
 * @time 2019/6/19 15:27
 * @description:
 */
public class test {
    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);
//        int  i=1;
//        int  j=i++;
//        if((j>++j)&&(i++==j)){j+=i;}
//        System.out.println(j);

//        outer: for (int i = 0; i < 3; i++)
//            inner: for (int j = 0; j < 2; j++) {
//                if (j == 1)
//                    continue outer;
//                System.out.println(j + " and " + i);
//            }
//
//        System.out.println(getValue(2));
//        System.out.println(Math.round(11.2));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        System.out.println(cal.getTime());
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        System.out.println(df.format(cal.getTime()));
    }

    public static int getValue(int i){
        int result=0;
        switch(i){
            case 1:
                result=result +i;
            case 2:
                result=result+i*2;
            case 3:
                result=result+i*3;
        }
        return result;
    }


}
