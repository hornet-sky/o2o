package my.ssm.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.LocalAuthDao;
import my.ssm.o2o.entity.LocalAuth;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.exception.LocalAuthException;
import my.ssm.o2o.service.LocalService;
import my.ssm.o2o.util.MD5Util;

/**  
 * <p>本地授权接口实现类</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */ 
@Service
public class LocalServiceImpl implements LocalService {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Transactional
    @Override
    public void createAndBindLocalAuth(String account, String password, UserInfo userInfo) {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setAccount(account);
        localAuth.setPassword(MD5Util.encrypt(password));
        localAuth.setCreateTime(new Date());
        localAuth.setUserInfo(userInfo);
        localAuthDao.save(localAuth);
    }

    @Override
    public LocalAuth findLocalAuthByUserId(Long userId) {
        LocalAuth localAuth = localAuthDao.findByUserId(userId);
        localAuth.setPassword(null);
        return localAuth;
    }

    @Override
    public boolean existsLocalAuth(Long userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        LocalAuth condition = new LocalAuth();
        condition.setUserInfo(userInfo);
        return localAuthDao.exists(condition);
    }

    @Transactional
    @Override
    public LocalAuth login(String account, String password) {
        LocalAuth localAuth = localAuthDao.findByAccountAndPassword(account, MD5Util.encrypt(password));
        if(localAuth == null) {
            throw new LocalAuthException("账号或密码错误");
        }
        Integer enableStatus = localAuth.getUserInfo().getEnableStatus();
        if(enableStatus == 0) {
            throw new LocalAuthException("该账号已被禁用");
        }
        if(enableStatus == 2) {
            throw new LocalAuthException("该账号正在审核中");
        }
        LocalAuth newProperties = new LocalAuth();
        newProperties.setLastLoginTime(new Date());
        LocalAuth condition = new LocalAuth();
        condition.setAccount(account);
        condition.setPassword(password);
        localAuthDao.update(newProperties, condition);
        localAuth.setPassword(null);
        return localAuth;
    }

    @Transactional
    @Override
    public LocalAuth changePassword(String account, String oldPassword, String newPassword) {
        oldPassword = MD5Util.encrypt(oldPassword);
        LocalAuth localAuth = localAuthDao.findByAccountAndPassword(account, oldPassword);
        if(localAuth == null) {
            throw new LocalAuthException("旧密码错误");
        }
        newPassword = MD5Util.encrypt(newPassword);
        Date now = new Date();
        LocalAuth newProperties = new LocalAuth();
        newProperties.setPassword(newPassword);
        newProperties.setLastEditTime(now);
        LocalAuth condition = new LocalAuth();
        condition.setAccount(account);
        condition.setPassword(oldPassword);
        localAuthDao.update(newProperties, condition);
        //返回修改后的本地授权信息
        localAuth.setPassword(null);
        localAuth.setLastEditTime(now);
        return localAuth;
    }

}
