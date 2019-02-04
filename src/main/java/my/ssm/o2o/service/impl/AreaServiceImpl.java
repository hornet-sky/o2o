package my.ssm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dao.AreaDao;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.service.AreaService;

/**  
 * <p>区域服务接口实现类</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */ 
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> findAll() {
        return areaDao.findAll();
    }

}
