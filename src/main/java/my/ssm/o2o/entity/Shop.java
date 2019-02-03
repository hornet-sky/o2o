package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>店铺</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Shop {
    /**  
     * <p>店铺类别ID</p>     
     */
    private Integer shopId;
    /**  
     * <p>店铺名称</p>     
     */
    private String shopName;
    /**  
     * <p>店铺说明</p>     
     */
    private String shopDesc;
    /**  
     * <p>店铺地址</p>     
     */
    private String shopAddr;
    /**  
     * <p>店铺图片</p>     
     */
    private String shopImg;
    /**  
     * <p>电话</p>     
     */
    private String phone;
    /**  
     * <p>超级管理员给店家的建议</p>     
     */
    private String advice;
    /**  
     * <p>权重（影响展示顺序）</p>     
     */
    private Integer priority;
    /**  
     * <p>店铺状态：-1 不可用，0 审核中，1 可用</p>     
     */
    private Integer enableStatus;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>修改时间</p>     
     */
    private Date lastEditTime;
    /**  
     * <p>店铺创建人</p>     
     */
    private UserInfo userInfo;
    /**  
     * <p>店铺所在区域</p>     
     */
    private Area area;
    /**  
     * <p>店铺类别</p>     
     */
    private ShopCategory shopCategory;
}