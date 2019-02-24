package my.ssm.o2o.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.exception.WechatException;

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
     * <p>性别：1 男，2 女，0 未知</p>     
     */
    @JsonProperty("sex")
    private Integer sex;
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
    
    /**  
     * <p>转换成本地系统用户</p>  
     * @return  本地系统用户
     */  
    public UserInfo toLocalUserInfo(Integer localUserType) {
        UserInfo local = new UserInfo();
        local.setGender(this.sex);
        local.setName(this.nickname);
        local.setProfileImg(this.headImgUrl);
        local.setUserType(localUserType);
        local.setCreateTime(new Date());
        if(localUserType == 1) { //用户类型：1 顾客，2 店家
            local.setEnableStatus(1); //用户状态：0 禁止使用本商城，1 允许使用本商城，2 审核中
        } else if(localUserType == 2) {
            local.setEnableStatus(2);
        } else {
            throw new WechatException("非法用户类型");
        }
        return local;
    }
}
