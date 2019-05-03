package my.ssm.o2o.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.PointsRecord;

/**  
 * <p>积分记录数据访问接口</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
public interface PointsRecordDao {
    /**  
     * <p>查找指定条件及分页参数的积分记录</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  积分记录列表
     */  
    List<PointsRecord> list(@Param("condition") PointsRecord condition, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的积分记录数量</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @return  积分记录数量
     */  
    long count(@Param("condition") PointsRecord condition, @Param("searchKey") String searchKey);
    /**  
     * <p>查找指定条件及分页参数的用户总积分记录</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  用户总积分记录列表
     */  
    List<Map<String, Object>> listConsumerTotalPointsRecordOnShopkeeperSide(@Param("shopId") Long shopId, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的用户总积分记录数量</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @return  用户总积分记录数量
     */  
    long countConsumerTotalPointsRecordOnShopkeeperSide(@Param("shopId") Long shopId, @Param("searchKey") String searchKey);
    /**  
     * <p>查找指定条件及分页参数的店铺总积分记录</p>  
     * @param consumerId 所属顾客ID
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  店铺总积分记录列表
     */  
    List<Map<String, Object>> listShopTotalPointsRecordOnConsumerSide(@Param("consumerId") Long consumerId, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的店铺总积分记录数量</p>  
     * @param consumerId 所属顾客ID
     * @param searchKey 查询关键字
     * @return  店铺总积分记录数量
     */  
    long countShopTotalPointsRecordOnConsumerSide(@Param("consumerId") Long consumerId, @Param("searchKey") String searchKey);
    /**  
     * <p>保存积分记录</p>  
     * @param award 待保存的积分记录
     * @return  影响的行数
     */  
    int save(PointsRecord pointsRecord);
    /**  
     * <p>查找指定条件及分页参数的店铺积分详情列表</p>  
     * @param consumerId 所属顾客ID
     * @param shopId 所属店铺ID
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param searchKey 关键字
     * @param isPointsInShow 是否查询获取积分的详情
     * @param isPointsOutShow 是否查询消耗积分的详情
     * @param pagingParams 分页参数
     * @return  店铺积分详情列表
     */  
    List<Map<String, Object>> listShopPointsRecordDetailOnConsumerSide(@Param("consumerId") Long consumerId, @Param("shopId") Long shopId, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("searchKey") String searchKey, @Param("isPointsInShow") Boolean isPointsInShow, @Param("isPointsOutShow") Boolean isPointsOutShow, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件及分页参数的店铺积分详情数量</p>  
     * @param consumerId 所属顾客ID
     * @param shopId 所属店铺ID
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param searchKey 关键字
     * @param isPointsInShow 是否查询获取积分的详情
     * @param isPointsOutShow 是否查询消耗积分的详情
     * @return  店铺积分详情数量
     */  
    long countShopPointsRecordDetailOnConsumerSide(@Param("consumerId") Long consumerId, @Param("shopId") Long shopId, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("searchKey") String searchKey, @Param("isPointsInShow") Boolean isPointsInShow, @Param("isPointsOutShow") Boolean isPointsOutShow);
}
