package priv.wzb.redis.stream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: redis
 * @author: yuzuki
 * @create: 2021-04-15 17:36
 * @description: redis stream 配置
 **/
@Component
@ConfigurationProperties(prefix = "stream")
@Data
public class RedisStreamConfig {
	private String stream;
	private String consumerGroup;
	private List<String> consumers;
	private String noticeKey;
	private Integer queueSize;
}
