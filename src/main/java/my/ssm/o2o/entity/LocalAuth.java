package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>本地授权</p>
 * <p>Date: 2019年2月3日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class LocalAuth {
    /**  
     * <p>本地授权ID</p>     
     */
    private Integer localAuthId;
    /**  
     * <p>用户名</p>     
     */
    private String username;
    /**  
     * <p>密码</p>     
     */
    private String password;
    /**  
     * <p>关联的用户</p>     
     */
    private UserInfo userInfo;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
}
