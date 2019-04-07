package my.ssm.o2o.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.ConsumptionRecord;

/**  
 * <p>消费记录数据访问接口</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
public interface ConsumptionRecordDao {
    /**  
     * <p>查找指定条件及分页参数的消费记录</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  消费记录列表
     */  
    List<ConsumptionRecord> list(@Param("condition") ConsumptionRecord condition, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的消费记录数量</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @return  消费记录数量
     */  
    long count(@Param("condition") ConsumptionRecord condition, @Param("searchKey") String searchKey);
    /**  
     * <p>保存消费记录</p>  
     * @param award 待保存的消费记录
     * @return  影响的行数
     */  
    int save(ConsumptionRecord consumptionRecord);
    /**  
     * <p>统计指定店铺、指定时间段的商品销量</p>  
     * @param shopId 店铺ID
     * @param beginDate 起始日期
     * @param endDate 终止日期
     * @param topN 按销量降序排列后的前N个商品销量
     * @return  前N个商品销量
     */  
    List<Map<String, Object>> countByShopIdAndDateRangeInEachGroup(@Param("shopId") Long shopId, 
            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("topN") Integer topN);
}
