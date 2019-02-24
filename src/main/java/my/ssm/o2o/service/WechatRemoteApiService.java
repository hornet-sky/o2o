package my.ssm.o2o.service;

import my.ssm.o2o.dto.WechatAccessToken;
import my.ssm.o2o.dto.WechatUserInfo;

/**  
 * <p>与微信远程交互接口</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public interface WechatRemoteApiService {
    /**  
     * <p>与微信的SnsApi交互以获取accessToken</p>  
     * @param code 微信公众号回传的code
     * @return  accessToken
     */  
    WechatAccessToken loadAccessToken(String code);
    /**  
     * <p>与微信的SnsApi交互以获取用户信息</p>  
     * @param accessToken 用于鉴定请求者身份的令牌
     * @param openId 微信用户的身份标识
     * @return  微信用户信息
     */  
    WechatUserInfo loadWechatUserInfo(String accessToken, String openId);
}
