package priv.wzb.design_pattern.structural_pattern.proxypattern.example;

/**
 * @author Satsuki
 * @time 2019/5/19 14:39
 * @description:身份验证类提供validate()方法来实现身份验证
 */
public class AccessValidator {
    //模拟实现登陆验证
    public boolean validate(String userId){
        System.out.println("在数据库中验证用户" + userId + "是否为合法用户");
        if(userId.equalsIgnoreCase("yukari")){
            System.out.println(userId+"登陆成功");
            return true;
        }else {
            System.out.println(userId + "登陆失败");
            return false;
        }
    }
}
