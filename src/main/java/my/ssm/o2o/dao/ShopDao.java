package my.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.Shop;

/**  
 * <p>店铺数据访问接口</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */  
public interface ShopDao {
    /**  
     * <p>查找指定ID的店铺信息</p>  
     * @param shopId 店铺ID
     * @return  店铺信息
     */  
    Shop findById(Long shopId);
    /**  
     * <p>查找指定条件及分页参数的店铺信息</p>  
     * @param condition 查询条件
     * @param searchKey 关键字
     * @param pagingParams 分页参数
     * @return  店铺信息列表
     */  
    List<Shop> list(@Param("condition") Shop condition, @Param("searchKey") String searchKey, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的店铺数量</p>  
     * @param condition 查询条件
     * @param searchKey 关键字
     * @return  店铺数量
     */  
    long count(@Param("condition") Shop condition, @Param("searchKey") String searchKey);
    /**  
     * <p>保存店铺</p>  
     * @param shop 待保存的店铺
     * @return  影响的行数
     */  
    int save(Shop shop);
    /**  
     * <p>更新店铺</p>  
     * @param shop 待更新的店铺
     * @return  影响的行数
     */  
    int update(Shop shop);
}
