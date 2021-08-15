package priv.wzb.s3;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @program:
 * @author: yuzuki
 * @create: 2020-12-21 17:11
 * @description: amazon s3 service
 **/
public interface AmazonService {
	/**
	 * 创建/获取S3 存储桶
	 * @param bucketName
	 * @return
	 */
	Bucket getOrCreateBucket(String bucketName);

	/**
	 * 列出S3 存储桶
	 * @return
	 */
	List<Bucket> listBuckets();

	/**
	 * 上传对象
	 * @param file 上传的文件
	 * @param filePathPrefix 文件前缀路径
	 * @return
	 */
	AmazonFileDTO upload(MultipartFile file,String filePathPrefix);

	/**
	 * 列出文件对象
	 * @param bucketName
	 * @return
	 */
	List<S3ObjectSummary> listObjects(String bucketName);

	/**
	 * 下载对象
	 * @param bucketName
	 * @param keyName
	 * @return
	 */
	S3Object downloadObject(String bucketName, String keyName);

	/**
	 * 删除对象
	 * @param keyName
	 */
	void deleteObject(String keyName);
}
