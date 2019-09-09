package priv.wzb.multi_threaded_high_concurrent.study;

/**
 * @author Satsuki
 * @time 2019/9/6 14:35
 * @description:
 */
public class TDownloader extends Thread {

    private String url;
    private String name;

    public TDownloader(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownloader wd = new WebDownloader();
        wd.download(url,name);
    }

    public static void main(String[] args) {
        TDownloader td1 = new TDownloader("https://i.pximg.net/img-original/img/2019/09/05/16/14/12/76633286_p0.jpg","pixiv/angel");
        TDownloader td2 = new TDownloader("https://i.pximg.net/img-original/img/2019/09/04/02/11/16/76613636_p0.png","pixiv/whiteDress");
        TDownloader td3 = new TDownloader("https://i.pximg.net/img-original/img/2019/09/04/01/29/42/76613152_p0.png","pixiv/miku");

        //启动三个线程
        td1.start();
        td2.start();
        td3.start();

        try {
            td1.join();
            td2.join();
            td3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
