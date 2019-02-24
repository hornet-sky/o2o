package my.ssm.o2o.service;

import java.io.IOException;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.entity.WechatAuth;
import my.ssm.o2o.util.UserUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatServiceTest extends BaseTest {
    @Autowired
    private WechatService wechatService;
    @Test
    public void testBFindWechatAuthByOpenId() throws IOException {
        WechatAuth wechatAuth = wechatService.findWechatAuthByOpenId("1001");
        System.out.println("wechatAuth = " + wechatAuth);
    }
    @Test
    public void testASave() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.setCreateTime(new Date());
        userInfo.setEnableStatus(1);
        userInfo.setGender(1);
        userInfo.setName("Tom");
        userInfo.setProfileImg("https://ws1.sinaimg.cn/large/a15b4afegy1fhsfdznep4j2020020web.jpg");
        userInfo.setUserType(1);
        
        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setCreateTime(new Date());
        wechatAuth.setOpenId("1001");
        wechatAuth.setUserInfo(userInfo);
        
        wechatService.save(wechatAuth);
    }
    @Test
    public void testCUpdateLocalUserOnly() throws IOException {
        WechatAuth wechatAuth = wechatService.findWechatAuthByOpenId("1001");
        UserInfo userInfo = wechatAuth.getUserInfo();
        UserInfo forUpdate = new UserInfo();
        forUpdate.setLastEditTime(new Date());
        forUpdate.setGender(2);
        forUpdate.setProfileImg("https://ws1.sinaimg.cn/large/a15b4afegy1fhsfdznep4j2020020web2.jpg");
        forUpdate.setUserType(UserUtil.appendCustomer(userInfo.getUserType(), 2));
        forUpdate.setUserId(userInfo.getUserId());
        wechatAuth.setUserInfo(forUpdate);
        wechatService.updateLocalUserOnly(wechatAuth);
    }
}
