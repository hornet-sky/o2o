package my.ssm.o2o.service;

import my.ssm.o2o.entity.LocalAuth;
import my.ssm.o2o.entity.UserInfo;

/**  
 * <p>本地授权接口</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
public interface LocalService {
    /**  
     * <p>创建并绑定本地授权信息</p>  
     * @param account  账号
     * @param password  密码
     * @param userInfo  用户信息
     */  
    void createAndBindLocalAuth(String account, String password, UserInfo userInfo);
    /**  
     * <p>判断指定用户ID的本地授权信息是否存在</p>  
     * @param userId 用户ID
     * @return  如果存在则返回true
     */  
    boolean existsLocalAuth(Long userId);
    /**  
     * <p>查找指定用户ID的本地授权信息</p>  
     * @param userId 用户ID
     * @return  本地授权信息
     */  
    LocalAuth findLocalAuthByUserId(Long userId);
    /**  
     * <p>登录本地系统</p>  
     * @param account 本地账号
     * @param password 密码
     * @return  本地授权信息
     */  
    LocalAuth login(String account, String password);
    /**  
     * <p>修改密码</p>  
     * @param account 本地账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return  修改后的本地授权信息
     */  
    LocalAuth changePassword(String account, String oldPassword, String newPassword);
}
