package priv.wzb.redis.stream.factory;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @author: yuzuki
 * @create: 2021-06-09 16:21
 * @description: 通知策略bean对象名枚举
 **/
@Getter
public enum NoticeStrategyBeanNameEnum {
	/**
	 * 通知参数策略bean对象枚举
	 */
	APPOINT_ACTIVITY_MANAGER_STRATEGY(1,"appointActivityManagerStrategy", "test[%s]");

	private Integer code;
	private String beanName;
	private String message;

	NoticeStrategyBeanNameEnum(Integer code, String beanName, String message) {
		this.code = code;
		this.beanName = beanName;
		this.message = message;
	}

	private static Map<Integer, NoticeStrategyBeanNameEnum> COM_MAP=  new HashMap<>();
	static {
		for (NoticeStrategyBeanNameEnum value : NoticeStrategyBeanNameEnum.values()) {
			COM_MAP.put(value.getCode(),value);
		}
	}
	public static NoticeStrategyBeanNameEnum getEnumByCode(Integer code){
		return COM_MAP.get(code);
	}
}
