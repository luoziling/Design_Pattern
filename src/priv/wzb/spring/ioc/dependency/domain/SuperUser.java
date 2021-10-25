package priv.wzb.spring.ioc.dependency.domain;

import lombok.Data;
import priv.wzb.spring.ioc.dependency.annotation.Super;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-21 19:27
 * @description: 超级用户
 **/
@Data
@Super
public class SuperUser extends User{
	private String address;
}
