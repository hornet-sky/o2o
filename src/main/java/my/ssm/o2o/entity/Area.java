package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>区域</p>
 * <p>Date: 2019年2月2日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Area {
    /**  
     * <p>区域ID</p>     
     */
    private Integer areaId;
    /**  
     * <p>区域名称</p>     
     */
    private String areaName;
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
}
