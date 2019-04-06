package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>奖品</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Award {
    /**  
     * <p>奖品ID</p>     
     */
    private Long awardId;
    /**  
     * <p>奖品名称</p>     
     */
    private String awardName;
    /**  
     * <p>奖品说明</p>     
     */
    private String awardDesc;
    /**  
     * <p>奖品图片地址</p>     
     */
    private String awardImg;
    /**  
     * <p>兑换需要消耗的积分</p>     
     */
    private Long points;
    /**  
     * <p>权重（影响展示顺序）</p>     
     */
    private Integer priority;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>修改时间</p>     
     */
    private Date lastEditTime;
    /**  
     * <p>奖品状态：0 下架，1 在前端展示</p>     
     */
    private Integer enableStatus;
    /**  
     * <p>所属店铺ID</p>     
     */
    private Long shopId;
}
