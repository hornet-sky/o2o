package my.ssm.o2o.service;

import java.util.List;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.ProductCategory;

/**  
 * <p>商品类别服务接口</p>
 * <p>Date: 2019年2月11日</p>
 * @author Wanghui    
 */  
public interface ProductCategoryService {
    /**  
     * <p>查找指定条件及分页参数的商品类别信息</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  查询结果
     */  
    PagingResult<ProductCategory> listProductCategory(ProductCategory condition, PagingParams pagingParams);
    /**  
     * <p>查找指定条件的商品类别信息</p>  
     * @param condition 查询条件
     * @return  查询结果
     */  
    List<ProductCategory> listProductCategory(ProductCategory condition);
    /**  
     * <p>删除指定条件的商品类别信息</p>  
     * @param condition  条件
     */  
    int delProductCategory(ProductCategory condition);
    /**  
     * <p>批量插入商品类别</p>  
     * @param productCategoryList 商品类别列表
     * @return  受影响的记录数
     */  
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
