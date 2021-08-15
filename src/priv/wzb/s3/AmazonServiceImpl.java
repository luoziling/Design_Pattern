package priv.wzb.s3;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @program:
 * @author: yuzuki
 * @create: 2020-12-21 17:16
 * @description:
 **/
@Slf4j
@Service
public class AmazonServiceImpl implements AmazonService {
	@Resource
	private S3Config s3Config;
	private AmazonS3 s3;

	@Override
	public Bucket getOrCreateBucket(String bucketName) {
		Bucket b = null;
		if (s3.doesBucketExistV2(bucketName)) {
			log.info("Bucket {} already exists.\n", bucketName);
			b = getBucket(bucketName);
		} else {
			try {
				b = s3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				log.error("获取/创建s3文件存储桶失败,失败原因：" + e);
				throw new RuntimeException("存储桶创建失败");
			}
		}
		return b;
	}

	public Bucket getBucket(String bucketName) {
		Bucket bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucketName)) {
				bucket = b;
			}
		}
		return bucket;
	}

	@Override
	public List<Bucket> listBuckets() {
		return s3.listBuckets();
	}

	@Override
	public AmazonFileDTO upload(MultipartFile file,String filePathPrefix) {
		Snowflake idWorker = IdUtil.createSnowflake(1,1);
		String originalFileName = file.getOriginalFilename();
		String key = filePathPrefix + idWorker.nextId();
		String contentType = file.getContentType();
		long fileSize = file.getSize();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(fileSize);
		try {
			s3.putObject(s3Config.getBucketName(), key, file.getInputStream(), objectMetadata);
		} catch (AmazonServiceException | IOException e) {
			log.error("s3文件上传失败，失败原因：" + e);
			throw new RuntimeException("s3上传失败");
		}
		AmazonFileDTO amazonFileDTO = new AmazonFileDTO ();
		amazonFileDTO.setFileName(originalFileName);
		amazonFileDTO.setFileSize(fileSize);
		amazonFileDTO.setFileType(contentType);
		amazonFileDTO.setFileKey(key);
		amazonFileDTO.setBucketName(s3Config.getBucketName());
		return amazonFileDTO ;
	}

	@Override
	public List<S3ObjectSummary> listObjects(String bucketName) {
		log.info("Objects in S3 bucket {}:\n", bucketName);
		ListObjectsV2Result result = s3.listObjectsV2(bucketName);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		for (S3ObjectSummary os : objects) {
			log.info("* " + os.getKey());
		}
		return objects;
	}

	@Override
	public S3Object downloadObject(String bucketName, String keyName) {
		log.info("Downloading {} from S3 bucket {}...\n", bucketName, keyName);
		try {
			return s3.getObject(bucketName, keyName);
		} catch (AmazonServiceException e) {
			log.error("s3文件下载，失败原因：" + e);
			throw new RuntimeException("s3下载失败");
		}
	}

	@Override
	public void deleteObject(String keyName) {
		log.info("Deleting object{} from S3 bucket: {}\n", keyName,
				s3Config.getBucketName());
		try {
			s3.deleteObject(s3Config.getBucketName(), keyName);
		} catch (AmazonServiceException e) {
			log.error("s3删除失败，失败原因：" + e);
			throw new RuntimeException("s3删除失败");
		}
		log.info("Done!");
	}

	@PostConstruct
	public void init(){
		ClientConfiguration config = new ClientConfiguration();

		AwsClientBuilder.EndpointConfiguration endpointConfig =
				new AwsClientBuilder.EndpointConfiguration(s3Config.getEndpoint(), "cn-north-1");

		AWSCredentials awsCredentials = new BasicAWSCredentials(s3Config.getAccessKey(),s3Config.getSecretKey());
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

		s3  = AmazonS3Client.builder()
				.withEndpointConfiguration(endpointConfig)
				.withClientConfiguration(config)
				.withCredentials(awsCredentialsProvider)
				.disableChunkedEncoding()
				.withPathStyleAccessEnabled(true)
				.build();
	}
}
