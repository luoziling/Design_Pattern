package priv.wzb.redis.stream.parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @author: yuzuki
 * @create: 2021-06-09 15:50
 * @description:
 **/
@Service("appointActivityManagerStrategy")
public class AppointActivityManagerStrategy implements NoticeParameterStrategy<String, String> {
	private JSONObject param;


	private static final String ACTION = "xxx发送通知-";

	@Override
	public void init(String body) {
		this.param = JSON.parseObject(body);
	}

	@Override
	public String getMessage() {
		return "";
	}

	@Override
	public List<String> listNotifiedPersons() {
		// 通知被指定者
		List<String> res = new ArrayList<>();
		return res;
	}
}
