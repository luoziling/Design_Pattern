package priv.wzb.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @program:
 * @author: yuzuki
 * @create: 2021-04-13 08:37
 * @description:
 **/
@Data
public class MajorUserLevelRelationExcelPO {
	@Excel(name = "大用户id",fixedIndex = 0)
	private Integer majorUserId;
	@Excel(name = "大用户上级用户ID（没有就填0",fixedIndex = 5)
	private Integer parentId;
	@Excel(name = "类型",fixedIndex = 4)
	private String majorUserType;
}
