package priv.wzb.excel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户层级关系表
 * </p>
 *
 * @author yuzuki
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("major_user_level_relation")
public class MajorUserLevelRelationPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("major_user_id")
    private Integer majorUserId;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("major_user_type")
    private Integer majorUserType;

    @TableField("status_code")
    private Boolean statusCode;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private String createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private String updateTime;
}
