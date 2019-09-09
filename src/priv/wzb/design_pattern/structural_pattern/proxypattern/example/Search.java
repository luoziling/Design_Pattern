package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:45
 * @description:抽象查询类
 */
public interface Search {
    public String doSearch(String userId,String keyword);
}
