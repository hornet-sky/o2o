package my.ssm.o2o.dao;

import my.ssm.o2o.entity.WechatAuth;

/**  
 * <p>微信授权数据访问接口</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public interface WechatAuthDao {
    /**  
     * <p>查找指定openId的授权信息</p>  
     * @param openId 微信用户身份标识
     * @return  授权信息
     */  
    WechatAuth findByOpenId(String openId);
    /**  
     * <p>保存授权信息</p>  
     * @param wechatAuth 待保存的授权信息
     * @return  影响的行数
     */  
    int save(WechatAuth wechatAuth);
}
