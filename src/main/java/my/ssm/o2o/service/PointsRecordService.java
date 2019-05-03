package my.ssm.o2o.service;

import java.util.Map;

import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.PointsRecord;

/**  
 * <p>积分记录服务接口</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
public interface PointsRecordService {
    /**  
     * <p>查找指定条件及分页参数的积分记录</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @param pageNo 页码，从1开始
     * @param pageSize 每页大小
     * @return  带分页特性的积分记录集合
     */  
    PagingResult<PointsRecord> listPointsRecordOnShopkeeperSide(Long shopId, String searchKey, Integer pageNo, Integer pageSize);
    /**  
     * <p>查找指定条件及分页参数的用户总积分记录</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @param pageNo 页码，从1开始
     * @param pageSize 每页大小
     * @return  带分页特性的积分记录集合
     */  
    PagingResult<Map<String, Object>> listConsumerTotalPointsRecordOnShopkeeperSide(Long shopId, String searchKey, Integer pageNo, Integer pageSize);
    /**  
     * <p>查找指定条件及分页参数的店铺总积分记录</p>  
     * @param consumerId 所属顾客ID
     * @param searchKey 查询关键字
     * @param pageNo 页码，从1开始
     * @param pageSize 每页大小
     * @return  带分页特性的积分记录集合
     */  
    PagingResult<Map<String, Object>> listShopTotalPointsRecordOnConsumerSide(Long consumerId, String searchKey, Integer pageNo, Integer pageSize);
    /**  
     * <p>查找指定条件及分页参数的店铺积分详情</p>  
     * @param consumerId 所属顾客ID
     * @param shopId 所属店铺ID
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param searchKey 关键字
     * @param isPointsInShow 是否查询获取积分的详情
     * @param isPointsOutShow 是否查询消耗积分的详情
     * @param pageNo 页码，从1开始
     * @param pageSize 每页大小
     * @return  带分页特性的店铺积分详情集合
     */  
    PagingResult<Map<String, Object>> listShopPointsRecordDetailOnConsumerSide(Long consumerId, Long shopId, String beginDate, String endDate, String searchKey, Boolean isPointsInShow, Boolean isPointsOutShow, Integer pageNo, Integer pageSize);
}
