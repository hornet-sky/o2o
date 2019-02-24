package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>微信授权信息（即微信用户与当前系统用户的一对一关联关系）</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class WechatAuth {
    /**  
     * <p>微信授权ID</p>     
     */
    private Integer wechatAuthId;
    /**  
     * <p>用户身份标识</p>     
     */
    private String openId;
    /**  
     * <p>关联的用户</p>     
     */
    private UserInfo userInfo;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
}
