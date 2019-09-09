package priv.wzb.multi_threaded_high_concurrent.study;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Satsuki
 * @time 2019/9/6 14:33
 * @description:
 */
public class WebDownloader {

    /**
     * 下载
     * @param url
     * @param name
     */
    public void download(String url,String name){
        try {
            FileUtils.copyURLToFile(new URL(url),new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
