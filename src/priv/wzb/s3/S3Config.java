package priv.wzb.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program:
 * @author: yuzuki
 * @create: 2020-12-21 17:08
 * @description: amazon s3 config
 **/
@Component
@ConfigurationProperties(prefix = "s3")
@Data
public class S3Config {
	private String endpoint;
	private String user;
	private String accessKey;
	private String secretKey;
	private String summaryBucketName;
	private String bucketName;
	private List<String> allowedSuffixes;
}
