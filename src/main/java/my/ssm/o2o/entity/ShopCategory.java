package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>店铺分类</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ShopCategory {
    /**  
     * <p>店铺类别ID</p>     
     */
    private Long shopCategoryId;
    /**  
     * <p>店铺类别名称</p>     
     */
    private String shopCategoryName;
    /**  
     * <p>店铺类别说明</p>     
     */
    private String shopCategoryDesc;
    /**  
     * <p>店铺类别图片</p>     
     */
    private String shopCategoryImg;
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
     * <p>父类别</p>     
     */
    private ShopCategory parent;
}
