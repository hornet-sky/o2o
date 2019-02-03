package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>商品详情图片</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProductImg {
    /**  
     * <p>图片ID</p>     
     */
    private Long productImgId;
    /**  
     * <p>图片地址</p>     
     */
    private String imgAddr;
    /**  
     * <p>图片说明</p>     
     */
    private String imgDesc;
    /**  
     * <p>权重（影响展示顺序）</p>     
     */
    private Integer priority;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>商品ID</p>     
     */
    private Long productId;
}
