package my.ssm.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**  
 * <p>用于访问微信SnsApi的token</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */  
@Getter
@Setter
@ToString
public class WechatAccessToken {
    /**  
     * <p>与微信SnsApi交互时用到的令牌</p>     
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**  
     * <p>令牌有效时间，单位：秒</p>     
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
    /**  
     * <p>用于更新过期令牌的新令牌</p>     
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**  
     * <p>微信用户在当前公众号下的身份标识，具有唯一性</p>     
     */
    @JsonProperty("openid")
    private String openId;
    /**  
     * <p>权限范围</p>     
     */
    @JsonProperty("scope")
    private String scope;
}
