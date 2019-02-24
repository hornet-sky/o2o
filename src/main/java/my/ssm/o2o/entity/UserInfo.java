package my.ssm.o2o.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**  
 * <p>用户信息</p>
 * <p>Date: 2019年2月2日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserInfo {
    /**  
     * <p>用户ID</p>     
     */
    private Long userId;
    /**  
     * <p>用户名</p>     
     */
    private String name;
    /**  
     * <p>头像</p>     
     */
    private String profileImg;
    /**  
     * <p>电子邮件</p>     
     */
    private String email;
    /**  
     * <p>性别：1 男，2 女，0 未知</p>     
     */
    private Integer gender;
    /**  
     * <p>用户状态：0 禁止使用本商城，1 允许使用本商城，2 审核中</p>     
     */
    private Integer enableStatus;
    /**  
     * <p>用户类型：1 顾客，2 店家，8 超级管理员 </p>     
     */
    private Integer userType;
    /**  
     * <p>创建时间</p>     
     */
    private Date createTime;
    /**  
     * <p>修改时间</p>     
     */
    private Date lastEditTime;
}
