package my.ssm.o2o.service;

import java.util.List;
import java.util.Map;

import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.ConsumptionRecord;

/**  
 * <p>消费记录服务接口</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
public interface ConsumptionRecordService {
    /**  
     * <p>查找指定条件及分页参数的消费记录</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @param pageNo 页码，从1开始
     * @param pageSize 每页大小
     * @return  带分页特性的消费记录集合
     */  
    PagingResult<ConsumptionRecord> listConsumptionRecordOnShopkeeperSide(Long shopId, String searchKey, Integer pageNo, Integer pageSize);
    /**  
     * <p>统计指定店铺最近三天的商品销量</p>  
     * @param shopId 店铺ID
     * @return  最近三天的商品销量
     */  
    List<List<Map<String, Object>>> listSalesVolumeForThreeDays(Long shopId);
}
