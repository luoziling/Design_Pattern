package priv.wzb.s3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program:
 * @author: yuzuki
 * @create: 2020-12-21 17:15
 * @description: amazon file detail
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmazonFileDTO {
	/** 文件大小 */
	private Long fileSize;
	/** 文件名称 */
	private String fileName;
	/** 文件URL */
	private String url;
	/** 云存储中的唯一标识 */
	private String fileKey;
	/** 文件类型 */
	private String fileType;
	/**
	 * 云存储桶名
	 */
	private String bucketName;
}
