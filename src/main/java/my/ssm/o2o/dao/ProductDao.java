package my.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.Product;

/**  
 * <p>商品数据访问接口</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
public interface ProductDao {
    /**  
     * <p>查找指定条件及分页参数的商品信息</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  商品信息列表
     */  
    List<Product> list(@Param("condition") Product condition, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的商品信息数量</p>  
     * @param condition 查询条件
     * @return  商品信息数量
     */  
    long count(@Param("condition") Product condition);
    /**  
     * <p>保存商品信息</p>  
     * @param shop 待保存的商品
     * @return  影响的行数
     */  
    int save(Product product);
    /**  
     * <p>更新商品信息</p>  
     * @param product 待更新的商品
     * @return  影响的行数
     */  
    int update(Product product);
}
