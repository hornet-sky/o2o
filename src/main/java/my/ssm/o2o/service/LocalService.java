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
}
