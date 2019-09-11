package priv.wzb.javabase.server.Server01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @author Satsuki
 * @time 2019/9/11 13:25
 * @description:
 */
public class Response {
    private BufferedWriter bw;
    //正文
    private StringBuilder content;
    //协议头信息*（请求行与请求头
    private StringBuilder headInfo;
    private int len;//正文字节数

    private final String blank =" ";
    private final String CRLF = "\r\n";


    public Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }

    public Response(Socket client) {
        this();
        try {
            bw =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }

    public Response(OutputStream os) {
        bw =new BufferedWriter(new OutputStreamWriter(os));
    }

    //动态添加内容
    public Response print(String info){
        content.append(info);
        len+=info.getBytes().length;
        return this;
    }

    //动态添加内容
    public Response println(String info){
        content.append(info);
        len+=(info+CRLF).getBytes().length;
        return this;
    }

    //推送信息
    public void pushToBrower(int code) throws IOException {
        if(headInfo == null){
            code = 500;
        }

        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
    }

    //构建头信息
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(blank);
        headInfo.append(code).append(blank);

        switch (code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("Not Found").append(CRLF);
                break;
            case 505:
                headInfo.append("Server Error").append(CRLF);
                break;
        }
        //2、响应头(最后一行存在空行):
			/*
			 Date:Mon,31Dec209904:25:57GMT
			Server:shsxt Server/0.0.1;charset=GBK
			Content-type:text/html
			Content-length:39725426
			 */
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }
}
