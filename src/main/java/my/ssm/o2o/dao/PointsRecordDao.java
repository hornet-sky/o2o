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
    List<Map<String, Object>> listConsumerTotalPointsRecord(@Param("shopId") Long shopId, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的用户总积分记录数量</p>  
     * @param shopId 所属店铺ID
     * @param searchKey 查询关键字
     * @return  用户总积分记录数量
     */  
    long countConsumerTotalPointsRecord(@Param("shopId") Long shopId, @Param("searchKey") String searchKey);
    /**  
     * <p>保存积分记录</p>  
     * @param award 待保存的积分记录
     * @return  影响的行数
     */  
    int save(PointsRecord pointsRecord);
}
