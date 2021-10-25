package priv.wzb.spring.ioc.java.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * PBI产品树结构表
 * </p>
 *
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("event_product_tree")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTreePO implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //节点ID
    @TableField("source_id")
    private Integer sourceId;

    //节点名称
    @TableField("name")
    private String name;

    //节点父项ID
    @TableField("parent_id")
    private Integer parentId;

    //层级 1，直属组织；2，经营单元；3，产品线；4，产品名称；5，产品系列；6，产品型号；7，软件版本
    @TableField("level")
    private Boolean level;

    //创建人
    @TableField("create_user")
    private String createUser;

    //创建时间
    @TableField("create_time")
    private Date createTime;

    //更新人
    @TableField("update_user")
    private String updateUser;

    //更新日期
    @TableField("update_time")
    private Date updateTime;

    //产品接口人
    @TableField("product_director_oa_serial")
    private String productDirectorOaSerial;

    //审批人
    @TableField("approver_oa_serial")
    private String approverOaSerial;

    //运营
    @TableField("operator_oa_serial")
    private String operatorOaSerial;

    @TableField("product_director")
    private String productDirector;

    @TableField("approver")
    private String approver;

    @TableField("operator")
    private String operator;

    //状态 1.有效-1无效
    @TableField(select = false)
    private Integer statusCode;
}
