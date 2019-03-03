package my.ssm.o2o.enums;

/**  
 * <p>本地授权操作状态枚举</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
public enum LocalOperStateEnum implements OperStateEnum {
    BIND_SUCCESS(1098, "绑定成功"), 
    OPERATION_SUCCESS(1099, "操作成功"), 
    LEGAL_PARAMETER(-1001, "不合法的参数"),
    LEGAL_ACCOUNT(-1002, "不合法的账号"),
    LEGAL_PASSWORD(-1003, "不合法的密码"),
    INVALID_VERIFY_CODE(-1004, "无效的验证码"),
    REPETITIVE_LOCAL_AUTH(-1005, "本地授权已存在"),
    OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private LocalOperStateEnum(Integer state, String msg) {
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
