package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Satsuki
 * @time 2019/5/19 15:19
 * @description:
 */
public class Client {
    public static void main(String[] args){
        InvocationHandler handler = null;
        AbstractUserDAO userDAO = new UserDAO();
        handler = new DAOLogHandler(userDAO);
        AbstractUserDAO proxy=null;

        /**
         *      * @param   loader the class loader to define the proxy class
         *      * @param   interfaces the list of interfaces for the proxy class
         *      *          to implement
         *      * @param   h the invocation handler to dispatch method invocations to
         */
        //动态创建代理对象，用于代理一个AbstractUserDAO类型的真实对象
        proxy = (AbstractUserDAO) Proxy.newProxyInstance(AbstractUserDAO.class.getClassLoader()
                ,new Class[]{AbstractUserDAO.class},handler);
        //调用代理对象的业务方法
        proxy.findUserById("Satsuki");
        System.out.println("-------------------------------------------------");

        AbstractDocumentDAO docDAO = new DocumentDAO();
        handler = new DAOLogHandler(docDAO);
        AbstractDocumentDAO proxy_new=null;

        //动态创建代理对象，用于代理一个AbstractDocumentDAO类型的真实对象
        proxy_new = (AbstractDocumentDAO)Proxy.newProxyInstance(AbstractDocumentDAO.class.getClassLoader(),new Class[]{AbstractDocumentDAO.class},handler);
        proxy_new.deleteDocumentById("D002");//调用代理对象的业务方法

    }
}
