package priv.wzb.spring.ioc.java.beans;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author: lxyer
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerExportVO {
    @ExcelProperty(value = "客户名称",order = 0)
    @Excel(name = "客户名称",orderNum = "0")
    private String customerName;
    @ExcelProperty(value = "行业中类",order = 1)
    @Excel(name = "行业中类",orderNum = "1")
    private String industryMiddleClass;
    @ExcelProperty(value = "行业小类",order = 2)
    @Excel(name = "行业小类",orderNum = "2")
    private String industrySmallClass;
    @ExcelProperty(value = "客户省份",order = 3)
    @Excel(name = "客户省份",orderNum = "3")
    private String province;
    @ExcelProperty(value = "客户城市",order = 4)
    @Excel(name = "客户城市",orderNum = "4")
    private String city;
    @ExcelProperty(value = "客户归属",order = 5)
    @Excel(name = "客户归属",orderNum = "5")
    private String organizationName;
    @ExcelProperty(value = "客户负责人",order = 6)
    @Excel(name = "客户负责人",orderNum = "6")
    private String manager;

    @ExcelIgnore
    private Integer productLineId;
    @ExcelProperty(value = "产品线",order = 7)
    @Excel(name = "产品线",orderNum = "7")
    private String productLine;

    @ExcelIgnore
    private Integer productNameId;
    @ExcelProperty(value = "产品名称",order = 8)
    @Excel(name = "产品名称",orderNum = "8")
    private String productName;

    @ExcelIgnore
    private Integer productSeriesId;
    @ExcelProperty(value = "产品系列",order = 9)
    @Excel(name = "产品系列",orderNum = "9")
    private String productSeries;

    @ExcelIgnore
    private Integer productTypeId;
    @ExcelProperty(value = "产品型号",order = 10)
    @Excel(name = "产品型号",orderNum = "10")
    private String productType;

    @ExcelIgnore
    private Integer productVersionId;
    @ExcelProperty(value = "产品版本",order = 11)
    @Excel(name = "产品版本",orderNum = "11")
    private String productVersion;

    @ExcelProperty(value = "IP地址",order = 12)
    @Excel(name = "IP地址",orderNum = "12")
    private String ip;
    @ExcelProperty(value = "产品授权码/序列号",order = 13)
    @Excel(name = "产品授权码/序列号",orderNum = "13")
    private String productSerialCode;

    @ExcelIgnore
    private Integer instanceRiskLevel;
    @ExcelProperty(value = "产品实例风险等级",order = 14)
    @Excel(name = "产品实例风险等级",orderNum = "14")
    private String instanceRiskLevelDesc;

    @ExcelProperty(value = "更新时间",order = 15)
    @Excel(name = "更新时间",orderNum = "15")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateTime;

    @ExcelIgnore
    private Integer stateCode;
    @ExcelProperty(value = "完成状态",order = 16)
    @Excel(name = "完成状态",orderNum = "16")
    private String stateCodeDesc;

    @ExcelProperty(value = "事件名称",order = 17)
    @Excel(name = "事件名称",orderNum = "17")
    private String eventName;

    @ExcelIgnore
    private Integer eventRiskLevel;
    @ExcelProperty(value = "事件风险等级",order = 18)
    @Excel(name = "事件风险等级",orderNum = "18")
    private String eventRiskLevelDesc;

    @ExcelIgnore
    private Integer noticeState;
    @ExcelProperty(value = "是否已发通知",order = 19)
    @Excel(name = "是否已发通知",orderNum = "19")
    private String noticeStateDesc;

    @ExcelIgnore
    private Integer riskState;
    @ExcelProperty(value = "风险是否解决",order = 20)
    @Excel(name = "风险是否解决",orderNum = "20")
    private String riskStateDesc;

    @ExcelIgnore
    private Integer dealState;
    @ExcelProperty(value = "处理状态",order = 21)
    @Excel(name = "处理状态",orderNum = "21")
    private String dealStateDesc;

    @ExcelIgnore
    private Integer updateState;
    @ExcelProperty(value = "是否升级",order = 22)
    @Excel(name = "是否升级",orderNum = "22")
    private String updateStateDesc;

    @ExcelProperty(value = "升级版本",order = 23)
    @Excel(name = "升级版本",orderNum = "23")
    private String version;

    @ExcelIgnore
    private Integer executeState;
    @ExcelProperty(value = "执行情况",order = 24)
    @Excel(name = "执行情况",orderNum = "24")
    private String executeStateDesc;

    @ExcelProperty(value = "备注",order = 25)
    @Excel(name = "备注",orderNum = "25")
    private String remark;
}
