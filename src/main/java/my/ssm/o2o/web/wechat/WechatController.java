package my.ssm.o2o.web.wechat;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.WechatAccessToken;
import my.ssm.o2o.dto.WechatUserInfo;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.entity.WechatAuth;
import my.ssm.o2o.service.WechatRemoteApiService;
import my.ssm.o2o.service.WechatService;
import my.ssm.o2o.util.URLEncoderUtil;
import my.ssm.o2o.util.UserUtil;

/**  
 * <p>与微信进行交互的控制器</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/wechat")
public class WechatController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WechatService wechatService;
    @Autowired
    private WechatRemoteApiService wechatRemoteApiService;
    /**  
     * <p>验证授权</p>  
     * @param signature 微信发来的加密签名。融合了开发者在公众平台里设置的token和当前时间戳、随机数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr  随机字符串，用于回显
     */
    @ResponseBody
    @GetMapping(value="/checkauthorization", produces="application/json;charset=utf-8")
    public String checkAuthorization(String signature, String timestamp, String nonce, String echostr) {
        try {
            if(wechatService.checkSignature(signature, timestamp, nonce)) { //如果校验成功，则回显echostr
                return echostr;
            }
        } catch (Exception e) {
            logger.error("验证授权时发生异常", e);
        }
        return null;
    }
    
    /**  
     * <p>微信用户通过公众号登录本地系统</p>  
     * @param code 微信公众号发来的鉴权码
     * @param localUserType 本地系统中的用户类型，1 顾客，2 店家
     * @param session HttpSession
     * @return  视图名
     */  
    @GetMapping("/login")
    public String login(@RequestParam("code") String code, 
            @RequestParam("state") Integer localUserType,
            HttpSession session) {
        try {
            //1、获取微信用户信息
            WechatAccessToken wechatAccessToken = wechatRemoteApiService.loadAccessToken(code);
            // TODO access_token在2小时内有效，过期需要重新获取，但1天内获取次数有限，开发者需自行存储
            logger.debug("wechatAccessToken={}", wechatAccessToken);
            String openId = wechatAccessToken.getOpenId();
            WechatUserInfo wechatUserInfo = wechatRemoteApiService.loadWechatUserInfo(wechatAccessToken.getAccessToken(), 
                    openId);
            logger.debug("wechatUserInfo={}", wechatUserInfo);
            //2、添加相关的本地用户信息，实现微信与本地系统的无缝对接
            WechatAuth wechatAuth = wechatService.findWechatAuthByOpenId(openId);
            if(wechatAuth == null) { //需要添加本地用户
                logger.debug("准备添加新用户");
                wechatAuth = new WechatAuth();
                wechatAuth.setOpenId(openId);
                wechatAuth.setCreateTime(new Date());
                UserInfo userInfo = wechatUserInfo.toLocalUserInfo(localUserType);
                wechatAuth.setUserInfo(userInfo);
                wechatService.save(wechatAuth);
                logger.debug("已添加新用户：{}", wechatAuth);
                if(localUserType == 1) { //顾客
                    session.setAttribute("user", userInfo);
                    session.setAttribute("openId", openId);
                    return "redirect:/frontend/index";
                }
                if(localUserType == 2) { //店家
                    session.setAttribute("user", userInfo);
                    session.setAttribute("openId", openId);
                    return "redirect:/shopadmin/shoplist";
                }
                return generateRedirectErrorPageStr("无法访问~", "您的身份未能识别！");
            }
            //wechatAuth不为空，说明已有相关的本地用户
            UserInfo userInfo = wechatAuth.getUserInfo();
            Long userId = userInfo.getUserId();
            Integer userType = userInfo.getUserType(); //用户类型：1 顾客，2 店家，8 超级管理员
            Integer enableStatus = userInfo.getEnableStatus(); //用户状态：0 禁止使用本商城，1 允许使用本商城，2 审核中
            if(enableStatus == 2 && localUserType == 2) { //只有店家需要审核，如果是顾客则直接放行
                return generateRedirectErrorPageStr("暂时无法访问~", "您提交的申请正在审核中...");
            }
            if(enableStatus == 0) {
                return generateRedirectErrorPageStr("禁止访问~", "您已经被关进小黑屋！");
            }
            UserInfo forUpdate = generateUserInfoForUpdate(wechatUserInfo, userInfo);
            if(localUserType == 1) { //当前访问者的身份是顾客
                if(!UserUtil.isCustomer(userType)) { //之前数据库里没有存储顾客身份
                    //追加顾客身份
                    if(forUpdate == null) forUpdate = new UserInfo();
                    forUpdate.setUserType(UserUtil.appendCustomer(userType, 1));
                }
                if(forUpdate != null) {
                    forUpdate.setUserId(userId);
                    wechatAuth.setUserInfo(forUpdate);
                    wechatService.updateLocalUserOnly(wechatAuth);
                }
                session.setAttribute("user", userInfo);
                session.setAttribute("openId", openId);
                return "redirect:/frontend/index";
            }
            if(localUserType == 2) { //当前访问者的身份是店家
                if(!UserUtil.isShopOwner(userType)) { //之前数据库里没有存储店家身份
                    //追加店家身份并将用户状态更新成“审核中”
                    if(forUpdate == null) forUpdate = new UserInfo();
                    forUpdate.setUserType(UserUtil.appendCustomer(userType, 2)); //追加店家身份
                    forUpdate.setEnableStatus(2); //审核中
                    forUpdate.setUserId(userId);
                    wechatAuth.setUserInfo(forUpdate);
                    wechatService.updateLocalUserOnly(wechatAuth);
                    return generateRedirectErrorPageStr("暂时无法访问~", "您提交的申请正在审核中...");
                }
                if(forUpdate != null) {
                    forUpdate.setUserId(userId);
                    wechatAuth.setUserInfo(forUpdate);
                    wechatService.updateLocalUserOnly(wechatAuth);
                }
                session.setAttribute("user", userInfo);
                session.setAttribute("openId", openId);
                return "redirect:/shopadmin/shoplist";
            }
            return generateRedirectErrorPageStr("无法访问~", "您的身份未能识别！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return generateRedirectErrorPageStr("系统出错了~", e.getMessage());
        }
    }
    
    private UserInfo generateUserInfoForUpdate(WechatUserInfo wechatUser, UserInfo oldLocalUser) {
        UserInfo newUserForUpdate = null;
        Integer sex = wechatUser.getSex();
        if(sex != null && !sex.equals(oldLocalUser.getGender())) {
            if(newUserForUpdate == null) newUserForUpdate = new UserInfo();
            newUserForUpdate.setGender(sex);
        }
        String nickname = wechatUser.getNickname();
        if(nickname != null && !nickname.equals(oldLocalUser.getName())) {
            if(newUserForUpdate == null) newUserForUpdate = new UserInfo();
            newUserForUpdate.setName(nickname);
        }
        String headImgUrl = wechatUser.getHeadImgUrl();
        if(headImgUrl != null && !headImgUrl.equals(oldLocalUser.getProfileImg())) {
            if(newUserForUpdate == null) newUserForUpdate = new UserInfo();
            newUserForUpdate.setProfileImg(headImgUrl);
        }
        return newUserForUpdate;
    }
    
    private String generateRedirectErrorPageStr(String caption, String content) {
        StringBuilder sb = new StringBuilder("redirect:/common/error?caption=").append(URLEncoderUtil.encode(caption))
                .append("&content=").append(URLEncoderUtil.encode(content));
        return sb.toString();
    }
}
