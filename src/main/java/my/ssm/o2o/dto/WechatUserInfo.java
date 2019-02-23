package my.ssm.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**  
 * <p>微信用户信息</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */
@Getter
@Setter
@ToString
public class WechatUserInfo {
    /**  
     * <p>微信用户在当前公众号下的身份标识，具有唯一性</p>     
     */
    @JsonProperty("openid")
    private String openId;
    /**  
     * <p>微信用户的昵称</p>     
     */
    @JsonProperty("nickname")
    private String nickname;
    /**  
     * <p>性别</p>     
     */
    @JsonProperty("sex")
    private String sex;
    /**  
     * <p>所在省份</p>     
     */
    @JsonProperty("province")
    private String province;
    /**  
     * <p>所在城市</p>     
     */
    @JsonProperty("city")
    private String city;
    /**  
     * <p>所在区、县</p>     
     */
    @JsonProperty("country")
    private String country;
    /**  
     * <p>头像</p>     
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;
    /**  
     * <p>语言</p>     
     */
    @JsonProperty("language")
    private String language;
    /**  
     * <p>用户权限</p>     
     */
    @JsonProperty("privilege")
    private String[] privilege;
}
