package my.ssm.o2o.enums;

/**  
 * <p>奖品操作状态枚举</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
public enum AwardOperStateEnum implements OperStateEnum {
    UNSHELVE(0, "该奖品已下架"), SHELVE(1, "奖品上架"), OPERATION_SUCCESS(1099, "操作成功"),
    AWARD_NOT_FOUND(-1001, "未找到奖品"), INITIALIZATION_FAILURE(-1003, "初始化失败"), 
    INVALID_VERIFY_CODE(-1004, "无效的验证码"),
    ILLEGAL_OPERATION(-1098, "非法操作"), OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private AwardOperStateEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }
    public Integer getState() {
        return state;
    }
    public String getMsg() {
        return msg;
    }
}
