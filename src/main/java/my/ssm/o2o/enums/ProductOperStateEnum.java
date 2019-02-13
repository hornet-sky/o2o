package my.ssm.o2o.enums;

/**  
 * <p>商品操作状态枚举</p>
 * <p>Date: 2019年2月11日</p>
 * @author Wanghui    
 */  
public enum ProductOperStateEnum implements OperStateEnum {
    UNSHELVE(0, "该商品已下架"), SHELVE(1, "商品上架"), OPERATION_SUCCESS(1099, "操作成功"),
    PRODUCT_NOT_FOUND(-1001, "未找到商品"), INITIALIZATION_FAILURE(-1003, "初始化失败"), 
    INVALID_VERIFY_CODE(-1004, "无效的验证码"), EMPTY_PRODUCT_CATEGORY(-1005, "空的商品类别集合"),
    ILLEGAL_OPERATION(-1098, "非法操作") ,OPERATION_FAILURE(-1099, "操作失败");
    private Integer state;
    private String msg;
    private ProductOperStateEnum(Integer state, String msg) {
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
