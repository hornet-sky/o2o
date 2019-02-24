package my.ssm.o2o.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dto.WechatAccessToken;
import my.ssm.o2o.dto.WechatUserInfo;
import my.ssm.o2o.exception.WechatException;
import my.ssm.o2o.service.WechatRemoteApiService;
import my.ssm.o2o.util.ClientUtil;
import my.ssm.o2o.util.JacksonUtil;

/**  
 * <p>与微信远程交互接口实现类</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */ 
@Service
public class WechatRemoteApiServiceImpl implements WechatRemoteApiService {
    @Value("#{prop['wechat.appid']}")
    private String appId;
    @Value("#{prop['wechat.secret']}")
    private String appSecret; //TODO 做混淆加密（包括数据库密码）
    @Value("#{prop['wechat.sns.accesstoken.url']}")
    private String accessTokenUrl;
    @Value("#{prop['wechat.sns.userinfo.url']}")
    private String wechatUserInfoUrl;
    @Value("#{prop['wechat.sns.userinfo.lang']}")
    private String lang;
    @Override
    public WechatAccessToken loadAccessToken(String code) throws WechatException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("appid", appId);
        parameters.put("secret", appSecret);
        parameters.put("code", code);
        parameters.put("grant_type", "authorization_code");
        try {
            String accessTokenJsonStr = ClientUtil.sslGet(accessTokenUrl, parameters , null);
            return JacksonUtil.parse(accessTokenJsonStr, WechatAccessToken.class);
        } catch (Exception e) {
            throw new WechatException("加载令牌期间产生异常", e);
        }
    }
    @Override
    public WechatUserInfo loadWechatUserInfo(String accessToken, String openId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_token", accessToken);
        parameters.put("openid", openId);
        parameters.put("lang", lang);
        try {
            String wechatUserInfoJsonStr = ClientUtil.sslGet(wechatUserInfoUrl, parameters , null);
            return JacksonUtil.parse(wechatUserInfoJsonStr, WechatUserInfo.class);
        } catch (Exception e) {
            throw new WechatException("加载微信用户信息期间产生异常", e);
        }
    }
    
}
