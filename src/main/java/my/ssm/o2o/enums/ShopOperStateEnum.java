package my.ssm.o2o.enums;

/**  
 * <p>店铺操作状态枚举</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public enum ShopOperStateEnum implements OperStateEnum {
    CHECKING(0, "审核中"), PASS(1, "审核通过"), OPERATION_SUCCESS(1099, "操作成功"),
    NO_PASS(-1, "审核不通过"), EMPTY_SHOP_ID(-1001, "店铺ID为空"), SHOP_NOT_FOUND(-1002, "未找到店铺"),
    INITIALIZATION_FAILURE(-1003, "初始化失败"), INVALID_VERIFY_CODE(-1004, "无效的验证码"),
    INVALID_IMAGE_TYPE(-1005, "无效的照片格式"),
    ILLEGAL_OPERATION(-1098, "非法操作") ,OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private ShopOperStateEnum(Integer state, String msg) {
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
