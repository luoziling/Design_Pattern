package priv.wzb.multi_threaded_high_concurrent.countdown_latch;

/**
 * @author Satsuki
 * @time 2020/5/25 13:39
 * @description:
 */
public enum CountryEnum {
    ONE(1,"齐"),TWO(2,"燕"),Three(3,"韩"),Four(4,"赵"),Five(5,"楚"),Six(6,"魏");

    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum value : values) {
            if (index == value.getRetCode()){
                return value;
            }
        }
        return null;
    }
}
