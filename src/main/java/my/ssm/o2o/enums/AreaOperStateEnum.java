package my.ssm.o2o.enums;

/**  
 * <p>区域操作状态枚举</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public enum AreaOperStateEnum implements OperStateEnum {
    OPERATION_SUCCESS(1099, "操作成功"), OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private AreaOperStateEnum(Integer state, String msg) {
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
