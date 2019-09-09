package priv.wzb.multi_threaded_high_concurrent.study;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Satsuki
 * @time 2019/9/6 14:35
 * @description:
 */
public class CDownloader implements Callable<Boolean> {

    private String url;
    private String name;

    public CDownloader(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        WebDownloader wd = new WebDownloader();
        wd.download(url,name);
        return true;
    }


    public static void main(String[] args) {
        CDownloader cd1 = new CDownloader("https://i.pximg.net/img-original/img/2019/09/05/16/14/12/76633286_p0.jpg","pixiv/angel");
        CDownloader cd2 = new CDownloader("https://i.pximg.net/img-original/img/2019/09/04/02/11/16/76613636_p0.png","pixiv/whiteDress");
        CDownloader cd3 = new CDownloader("https://i.pximg.net/img-original/img/2019/09/04/01/29/42/76613152_p0.png","pixiv/miku");

        //创建执行服务（线程池
        ExecutorService ser = Executors.newFixedThreadPool(3);

        //提交执行
        Future<Boolean> re1 = ser.submit(cd1);
        Future<Boolean> re2 = ser.submit(cd2);
        Future<Boolean> re3 = ser.submit(cd3);

        //关闭服务
        ser.shutdown();

    }
}
