package my.ssm.o2o.service;

import java.security.NoSuchAlgorithmException;

import my.ssm.o2o.entity.WechatAuth;

/**  
 * <p>有关微信的本地操作接口</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public interface WechatService {
    /**  
     * <p>校验签名</p>  
     * @param signature 微信发来的加密签名。融合了开发者在公众平台里设置的token和当前时间戳、随机数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return  校验结果。本地保存的token与微信发来的时间戳、随机数融合运算，生成新的签名，然后将新签名与微信发来的签名进行比较，如果相同则返回true。
     * @throws NoSuchAlgorithmException  如果系统不支持SHA-1算法则抛出该异常
     */  
    boolean checkSignature(String signature, String timestamp, String nonce) throws NoSuchAlgorithmException;
    /**  
     * <p>查找指定openId的授权信息</p>  
     * @param openId 微信用户身份标识
     * @return  授权信息
     */  
    WechatAuth findWechatAuthByOpenId(String openId);
    /**  
     * <p>保存微信授权信息</p>  
     * @param wechatAuth 微信授权信息（即微信用户与当前系统用户的一对一关联关系）
     */  
    void save(WechatAuth wechatAuth);
    /**  
     * <p>只更新本地用户</p>  
     * @param wechatAuth  微信授权信息（即微信用户与当前系统用户的一对一关联关系）
     */  
    void updateLocalUserOnly(WechatAuth wechatAuth);
}
