package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>商品分类</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProductCategory {
    /**  
     * <p>商品类别ID</p>     
     */
    private Long productCategoryId;
    /**  
     * <p>商品类别名称</p>     
     */
    private String productCategoryName;
    /**  
     * <p>权重（影响展示顺序）</p>     
     */
    private Integer priority;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>店铺ID</p>     
     */
    private Long shopId;
}
