package my.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.Award;

/**  
 * <p>奖品数据访问接口</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
public interface AwardDao {
    /**  
     * <p>查询指定ID的奖品信息</p>  
     * @param awardId 奖品ID
     * @return  奖品信息
     */  
    Award findById(Long awardId);
    /**  
     * <p>查找指定条件及分页参数的奖品信息</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  奖品信息列表
     */  
    List<Award> list(@Param("condition") Award condition, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的奖品信息数量</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @return  奖品信息数量
     */  
    long count(@Param("condition") Award condition, @Param("searchKey") String searchKey);
    /**  
     * <p>保存奖品信息</p>  
     * @param award 待保存的奖品
     * @return  影响的行数
     */  
    int save(Award award);
    /**  
     * <p>更新奖品信息</p>  
     * @param award 待更新的奖品
     * @return  影响的行数
     */  
    int update(Award award);
}
