package my.ssm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dao.HeadLineDao;
import my.ssm.o2o.entity.HeadLine;
import my.ssm.o2o.service.HeadLineService;

/**  
 * <p>前端滚动条服务接口实现类</p>
 * <p>Date: 2019年2月16日</p>
 * @author Wanghui    
 */ 
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> findByCondition(HeadLine condition) {
        return headLineDao.findByCondition(condition);
    }

}
