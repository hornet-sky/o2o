package my.ssm.o2o.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<List<Map<String, Object>>> listSalesVolumeForThreeDays(Long shopId) {
        List<List<Map<String, Object>>> salesVolumeForThreeDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Date today = calendar.getTime(); //今天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime(); //昨天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date theDayBeforeYesterday = calendar.getTime(); //前天
        Integer topN = 10;
        salesVolumeForThreeDays.add(consumptionRecordDao.countByShopIdAndDateRangeInEachGroup(shopId, theDayBeforeYesterday, yesterday, topN));
        salesVolumeForThreeDays.add(consumptionRecordDao.countByShopIdAndDateRangeInEachGroup(shopId, yesterday, today, topN));
        salesVolumeForThreeDays.add(consumptionRecordDao.countByShopIdAndDateRangeInEachGroup(shopId, today, null, topN));
        return salesVolumeForThreeDays;
    }

    @Override
    public PagingResult<ConsumptionRecord> listConsumptionRecordOnConsumerSide(Long consumerId, String searchKey,
            Integer pageNo, Integer pageSize) {
        PagingParams pagingParams = new PagingParams(pageNo, pageSize, "create_time", Direction.DESC);
        ConsumptionRecord condition = new ConsumptionRecord();
        condition.setConsumerId(consumerId);
        condition.setConsumerVisible(true);
        condition.setValid(true);
        List<ConsumptionRecord> list = consumptionRecordDao.list(condition, searchKey, pagingParams);
        long count = consumptionRecordDao.count(condition, searchKey);
        return new PagingResult<ConsumptionRecord>(list, count);
    }
}
