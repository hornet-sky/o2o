package my.ssm.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.ssm.o2o.dao.HeadLineDao;
import my.ssm.o2o.dao.cache.CacheDao;
import my.ssm.o2o.entity.HeadLine;
import my.ssm.o2o.exception.AreaException;
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
    @Autowired
    private CacheDao cacheDao;
    @Override
    public List<HeadLine> findByCondition(HeadLine condition) {
        Integer enableStatus = condition.getEnableStatus();
        String key = HEAD_LINE_LIST + (enableStatus == null ? "" : enableStatus.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(cacheDao.exists(key)) {
                String val = cacheDao.val(key);
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
                return mapper.readValue(val, type);
            }
            List<HeadLine> headLineList = headLineDao.findByCondition(condition);
            String headLineListJson = mapper.writeValueAsString(headLineList);
            cacheDao.set(key, headLineListJson);
            return headLineList;
        } catch (Exception e) {
            throw new AreaException("查询头条列表期间产生异常", e);
        }
    }

}
