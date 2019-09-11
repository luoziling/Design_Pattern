package priv.wzb.javabase.server.Server01;



/**
 * @author Satsuki
 * @time 2019/9/10 21:35
 * @description:
 */
public class LoginServlet implements Servlet {
    @Override
    public void service(Request2 request,Response response) {
        System.out.println("LoginServlet");

        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("第一个servlet");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("shsxt server终于回来了。。。。"+request.getParameter("uname"));
        response.print("</body>");
        response.print("</html>");
    }
}
