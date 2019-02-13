package my.ssm.o2o.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>商品</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Product {
    /**  
     * <p>商品ID</p>     
     */
    private Long productId;
    /**  
     * <p>商品名称</p>     
     */
    private String productName;
    /**  
     * <p>商品说明</p>     
     */
    private String productDesc;
    /**  
     * <p>缩略图地址</p>     
     */
    private String imgAddr;
    /**  
     * <p>正常价格</p>     
     */
    private String normalPrice;
    /**  
     * <p>促销价格</p>     
     */
    private String promotionPrice;
    /**  
     * <p>奖励积分</p>     
     */
    private Long rewardsPoints;
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
     * <p>商品状态：0 下架，1 在前端展示</p>     
     */
    private Integer enableStatus;
    /**  
     * <p>商品图片</p>     
     */
    private List<ProductImg> productImgList;
    /**  
     * <p>商品类别</p>     
     */
    private ProductCategory productCategory;
    /**  
     * <p>所属商铺</p>     
     */
    private Shop shop;
}
