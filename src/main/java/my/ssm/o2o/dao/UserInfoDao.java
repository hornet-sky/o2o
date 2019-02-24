package my.ssm.o2o.dao;

import my.ssm.o2o.entity.UserInfo;

/**  
 * <p>用户信息数据访问接口</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public interface UserInfoDao {
    /**  
     * <p>查找指定ID的用户信息</p>  
     * @param userId 用户ID
     * @return  用户信息
     */  
    UserInfo findById(Long userId);
    /**  
     * <p>保存用户信息</p>  
     * @param userInfo 待保存的用户信息
     * @return  影响的行数
     */  
    int save(UserInfo userInfo);
    /**  
     * <p>更新用户信息</p>  
     * @param userInfo 待更新的用户信息
     * @return  影响的行数
     */  
    int update(UserInfo userInfo);
}
