package my.ssm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dao.ConsumptionRecordDao;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.ConsumptionRecord;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.service.ConsumptionRecordService;

/**  
 * <p>消费记录服务接口实现类</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
@Service
public class ConsumptionRecordServiceImpl implements ConsumptionRecordService {
    @Autowired
    private ConsumptionRecordDao consumptionRecordDao;

    @Override
    public PagingResult<ConsumptionRecord> listConsumptionRecordOnShopkeeperSide(Long shopId, String searchKey, Integer pageNo,
            Integer pageSize) {
        PagingParams pagingParams = new PagingParams(pageNo, pageSize, "create_time", Direction.DESC);
        ConsumptionRecord condition = new ConsumptionRecord();
        condition.setShopId(shopId);
        condition.setShopkeeperVisible(true);
        condition.setValid(true);
        List<ConsumptionRecord> list = consumptionRecordDao.list(condition, searchKey, pagingParams);
        long count = consumptionRecordDao.count(condition, searchKey);
        return new PagingResult<ConsumptionRecord>(list, count);
    }
}
