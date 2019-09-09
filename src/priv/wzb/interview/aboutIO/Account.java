package priv.wzb.interview.aboutIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Satsuki
 * @time 2019/7/6 13:09
 * @description:写一个方法，输入一个文件名和一个字符串，统计这个字符串在这个文件中出现的次数。
 */
public class Account {
    private Account(){
        throw new AssertionError();
    }

    /**
     * 统计给定文件中给定字符串的出现次数
     * @param filename 文件名
     * @param word 字符串
     * @return 字符串在文件中出现的次数
     */
    public static int countWordInFile(String filename,String word){
        int counter = 0;
        try(FileReader fr = new FileReader(filename)) {
            try(BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                while ((line = br.readLine())!=null){
                    int index = -1;
                    while (line.length()>=word.length()&&(index=line.indexOf(word))>0){
                        counter++;
                        line = line.substring(index+word.length());
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return counter;
    }
}
