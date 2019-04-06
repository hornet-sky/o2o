package my.ssm.o2o.enums;

/**  
 * <p>积分记录操作状态枚举</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
public enum PointsRecordOperStateEnum implements OperStateEnum {
    OPERATION_SUCCESS(1099, "操作成功"), INITIALIZATION_FAILURE(-1003, "初始化失败"), 
    ILLEGAL_OPERATION(-1098, "非法操作"), OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private PointsRecordOperStateEnum(Integer state, String msg) {
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
