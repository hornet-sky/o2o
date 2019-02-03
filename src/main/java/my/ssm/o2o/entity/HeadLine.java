package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>头条</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class HeadLine {
    /**  
     * <p>头条ID</p>     
     */
    private Long lineId;
    /**  
     * <p>头条名字</p>     
     */
    private String lineName;
    /**  
     * <p>头条链接</p>     
     */
    private String lineLink;
    /**  
     * <p>头条图片</p>     
     */
    private String lineImg;
    /**  
     * <p>权重（影响展示顺序）</p>     
     */
    private Integer priority;
    /**  
     * <p>头条状态：0 不可用，1 可用</p>     
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
}
