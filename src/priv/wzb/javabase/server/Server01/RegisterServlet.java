package priv.wzb.javabase.server.Server01;

/**
 * @author Satsuki
 * @time 2019/9/10 21:35
 * @description:
 */
public class RegisterServlet implements Servlet {
    @Override
    public void service(Request2 request,Response response) {
        System.out.println("RegisterServlet");
        response.print("注册成了");
    }
}
