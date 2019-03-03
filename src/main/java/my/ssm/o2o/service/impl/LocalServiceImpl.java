package my.ssm.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.LocalAuthDao;
import my.ssm.o2o.entity.LocalAuth;
import my.ssm.o2o.entity.UserInfo;
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
        return localAuthDao.findByUserId(userId);
    }

    @Override
    public boolean existsLocalAuth(Long userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        LocalAuth condition = new LocalAuth();
        condition.setUserInfo(userInfo);
        return localAuthDao.exists(condition);
    }

}
