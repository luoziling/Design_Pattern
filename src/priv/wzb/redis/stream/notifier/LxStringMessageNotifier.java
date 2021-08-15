package priv.wzb.redis.stream.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @author: yuzuki
 * @create: 2021-06-09 15:41
 * @description: 普通文字通知者
 **/
@Service
@Slf4j
public class LxStringMessageNotifier implements Notifier<String,String> {

	@Override
	public void sendNotice(String message, List<String> noticedPersons) {
		List<String> lxNoticeList = new ArrayList<>();
		for (String oaSerial : noticedPersons) {
			try {
				// 发送通知
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * user_id 首字母转大写
	 *
	 * @param str
	 * @return
	 */
	public String upperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
