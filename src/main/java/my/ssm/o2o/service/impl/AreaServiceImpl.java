package my.ssm.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.ssm.o2o.dao.AreaDao;
import my.ssm.o2o.dao.cache.CacheDao;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.exception.AreaException;
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
    @Autowired
    private CacheDao cacheDao;
    @Override
    public List<Area> findAll() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(cacheDao.exists(AREA_LIST)) {
                String val = cacheDao.val(AREA_LIST);
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
                return mapper.readValue(val, type);
            }
            List<Area> areaList = areaDao.findAll();
            String areaListJson = mapper.writeValueAsString(areaList);
            cacheDao.set(AREA_LIST, areaListJson);
            return areaList;
        } catch (Exception e) {
            throw new AreaException("查询区域列表期间产生异常", e);
        }
    }

}
