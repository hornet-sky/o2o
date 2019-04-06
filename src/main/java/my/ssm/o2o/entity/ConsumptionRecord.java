package my.ssm.o2o.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>消费记录</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ConsumptionRecord {
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
     * <p>花费</p>     
     */
    private BigDecimal expenditure;
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
     * <p>消费是否有效：0 无效，1 有效</p>     
     */
    private Boolean valid;
}
