package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:46
 * @description:具体查询类
 */
public class RealSearcher implements Search {
    @Override
    public String doSearch(String userId, String keyword) {
        System.out.println("用户"+userId+"使用关键词"+keyword+"查询商务信息");
        return "返回具体内容";
    }
}
