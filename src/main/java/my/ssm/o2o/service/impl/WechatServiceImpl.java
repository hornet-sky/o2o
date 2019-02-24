package my.ssm.o2o.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.UserInfoDao;
import my.ssm.o2o.dao.WechatAuthDao;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.entity.WechatAuth;
import my.ssm.o2o.service.WechatService;
import my.ssm.o2o.util.SignUtil;
/**  
 * <p>有关微信的本地操作接口实现类</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
@Service
public class WechatServiceImpl implements WechatService {
    @Value("#{prop['wechat.token']}")
    private String token;
    @Autowired
    private WechatAuthDao wechatAuthDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce) throws NoSuchAlgorithmException {
        return SignUtil.checkSignature(signature, timestamp, nonce, token);
    }

    @Override
    public WechatAuth findWechatAuthByOpenId(String openId) {
        return wechatAuthDao.findByOpenId(openId);
    }

    @Transactional
    @Override
    public void save(WechatAuth wechatAuth) {
        //1、先保存本地用户
        userInfoDao.save(wechatAuth.getUserInfo()); //生成了userInfo的主键
        //2、再保存本地用户与微信用户的关联关系
        wechatAuthDao.save(wechatAuth);
    }

    @Transactional
    @Override
    public void updateLocalUserOnly(WechatAuth wechatAuth) {
        UserInfo userInfo = wechatAuth.getUserInfo();
        userInfo.setLastEditTime(new Date());
        userInfoDao.update(userInfo);
    }
}
