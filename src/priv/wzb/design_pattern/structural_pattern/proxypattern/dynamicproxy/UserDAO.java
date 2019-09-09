package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxy;

/**
 * @author Satsuki
 * @time 2019/5/19 15:04
 * @description:
 */
public class UserDAO implements AbstractUserDAO {
    @Override
    public Boolean findUserById(String userId) {
        if(userId.equalsIgnoreCase("Satsuki")){
            System.out.println("查询ID"+userId+"的用户信息成功");
            return true;
        }else {
            System.out.println("查询ID"+userId+"的用户信息失败");
            return false;
        }
    }
}
