package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:47
 * @description:代理查询类
 */
public class ProxySearcher implements Search{
    //维持一共对真实主题的引用
    private RealSearcher realSearcher = new RealSearcher();
    private AccessValidator accessValidator;
    private Logger logger;

    @Override
    public String doSearch(String userId, String keyword) {
        //如果身份验证成功则执行查询
        if(this.validate(userId)){
            //调用真实对象的查询方法
            String result = realSearcher.doSearch(userId,keyword);
            //记录查询日志
            this.log(userId);
            //返回查询结果
            return result;
        }
        return null;
    }

    //创建验证对象并调用其validate()方法进行身份验证
    public boolean validate(String userId){
        accessValidator = new AccessValidator();
        return accessValidator.validate(userId);
    }

    //创建日志记录对象并调用其log()方法实现日志记录
    public void log(String userId){
        logger = new Logger();
        logger.log(userId);
    }
}
