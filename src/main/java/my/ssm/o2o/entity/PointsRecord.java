package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>积分记录</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class PointsRecord {
    /**  
     * <p>记录ID</p>     
     */
    private Long recordId;
    /**  
     * <p>顾客ID</p>     
     */
    private Long consumerId;
    /**  
     * <p>顾客名称</p>     
     */
    private String consumerName;
    /**  
     * <p>店铺ID</p>     
     */
    private Long shopId;
    /**  
     * <p>店铺名称</p>     
     */
    private String shopName;
    /**  
     * <p>商品ID</p>     
     */
    private Long productId;
    /**  
     * <p>商品名称</p>     
     */
    private String productName;
    /**  
     * <p>正值为获得的积分，负值为消耗的积分</p>     
     */
    private Long points;
    /**  
     * <p>积分操作类型：-1 消费积分，1 获取积分</p>     
     */
    private Integer operType;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>顾客是否可见：0 不可见，1 可见</p>     
     */
    private Boolean consumerVisible;
    /**  
     * <p>商家是否可见：0 不可见，1 可见</p>     
     */
    private Boolean shopkeeperVisible;
    /**  
     * <p>积分是否有效：0 无效，1 有效</p>     
     */
    private Boolean valid;
}
