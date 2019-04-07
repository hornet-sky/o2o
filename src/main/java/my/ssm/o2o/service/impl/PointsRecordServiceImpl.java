package my.ssm.o2o.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dao.PointsRecordDao;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.PointsRecord;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.service.PointsRecordService;

/**  
 * <p>积分记录服务接口实现类</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
@Service
public class PointsRecordServiceImpl implements PointsRecordService {
    @Autowired
    private PointsRecordDao pointsRecordDao;

    @Override
    public PagingResult<PointsRecord> listPointsRecordOnShopkeeperSide(Long shopId, String searchKey, Integer pageNo,
            Integer pageSize) {
        PagingParams pagingParams = new PagingParams(pageNo, pageSize, "create_time", Direction.DESC);
        PointsRecord condition = new PointsRecord();
        condition.setShopId(shopId);
        condition.setShopkeeperVisible(true);
        condition.setValid(true);
        condition.setOperType(-1); //-1 消费积分，1 获取积分
        List<PointsRecord> list = pointsRecordDao.list(condition, searchKey, pagingParams);
        long count = pointsRecordDao.count(condition, searchKey);
        return new PagingResult<PointsRecord>(list, count);
    }

    @Override
    public PagingResult<Map<String, Object>> listConsumerTotalPointsRecordOnShopkeeperSide(Long shopId, String searchKey,
            Integer pageNo, Integer pageSize) {
        PagingParams pagingParams = new PagingParams(pageNo, pageSize);
        List<Map<String, Object>> list = pointsRecordDao.listConsumerTotalPointsRecordOnShopkeeperSide(shopId, searchKey, pagingParams);
        long count = pointsRecordDao.countConsumerTotalPointsRecordOnShopkeeperSide(shopId, searchKey);
        return new PagingResult<Map<String, Object>>(list, count);
    }

    @Override
    public PagingResult<Map<String, Object>> listShopTotalPointsRecordOnConsumerSide(Long consumerId, String searchKey,
            Integer pageNo, Integer pageSize) {
        PagingParams pagingParams = new PagingParams(pageNo, pageSize);
        List<Map<String, Object>> list = pointsRecordDao.listShopTotalPointsRecordOnConsumerSide(consumerId, searchKey, pagingParams);
        long count = pointsRecordDao.countShopTotalPointsRecordOnConsumerSide(consumerId, searchKey);
        return new PagingResult<Map<String, Object>>(list, count);
    }
}
