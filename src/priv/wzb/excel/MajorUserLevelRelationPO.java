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

    //大用户id
    @TableField("major_customer_id")
    private Integer majorCustomerId;

    //大用户上级id
    @TableField("parent_id")
    private Integer parentId;

    //大用户类别，枚举：1.头部用户 2.二级用户 3.三级用户 4.四级用户 5.五级用户
    @TableField("major_customer_type")
    private Integer majorCustomerType;

    //逻辑删除状态：1，有效；-1，无效
    @TableField("status_code")
    private Boolean statusCode;

    //创建人
    @TableField("create_user")
    private String createUser;

    //创建时间
    @TableField("create_time")
    private String createTime;

    //修改人
    @TableField("update_user")
    private String updateUser;

    //修改时间
    @TableField("update_time")
    private String updateTime;
}
