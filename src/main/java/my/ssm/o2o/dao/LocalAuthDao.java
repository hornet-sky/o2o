package my.ssm.o2o.dao;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.entity.LocalAuth;

/**  
 * <p>本地授权数据访问接口</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
public interface LocalAuthDao {
    /**  
     * <p>查找指定userId的本地授权信息</p>  
     * @param userId 用户信息ID
     * @return  本地授权信息
     */  
    LocalAuth findByUserId(Long userId);
    /**  
     * <p>查找指定账号和密码的本地授权信息</p>  
     * @param account 账号
     * @param password 密码
     * @return  本地授权信息
     */  
    LocalAuth findByAccountAndPassword(@Param("account") String account, @Param("password") String password);
    /**  
     * <p>判断指定条件的本地授权信息是否存在</p>  
     * @param condition 指定条件
     * @return  如果存在则返回true
     */  
    boolean exists(LocalAuth condition);
    /**  
     * <p>保存授权信息</p>  
     * @param localAuth 待保存的授权信息
     * @return  影响的行数
     */  
    int save(LocalAuth localAuth);
    /**  
     * <p>更新授权信息</p>  
     * @param newProperties 新的授权信息
     * @param condition 需要满足的更新条件
     * @return  影响的行数
     */  
    int update(@Param("newProperties") LocalAuth newProperties, @Param("condition") LocalAuth condition);
}
