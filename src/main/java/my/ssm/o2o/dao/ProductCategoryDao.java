package my.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.ProductCategory;

/**  
 * <p>商品类别数据访问接口</p>
 * <p>Date: 2019年2月11日</p>
 * @author Wanghui    
 */  
public interface ProductCategoryDao {
    /**  
     * <p>查找指定条件及分页参数的商品类别信息</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  商品类别列表
     */  
    List<ProductCategory> list(@Param("condition") ProductCategory condition, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>统计指定条件的商品类别数量</p>  
     * @param condition 查询条件
     * @return  商品类别数量
     */  
    long count(@Param("condition") ProductCategory condition);
    /**  
     * <p>删除指定条件的商品类别信息</p>  
     * @param condition 条件
     * @return  受影响的记录数
     */  
    int delete(@Param("condition") ProductCategory condition);
    /**  
     * <p>批量插入商品类别</p>  
     * @param productCategoryList 商品类别列表
     * @return  受影响的记录数
     */  
    int batchInsert(@Param("productCategoryList") List<ProductCategory> productCategoryList);
}
