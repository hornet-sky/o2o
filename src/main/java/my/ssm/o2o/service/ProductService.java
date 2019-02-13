package my.ssm.o2o.service;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Product;

/**  
 * <p>商品服务接口</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
public interface ProductService {
    /**  
     * <p>查找指定条件及分页参数的商品类别信息</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  查询结果
     */  
    PagingResult<Product> listProduct(Product condition, PagingParams pagingParams);
    /**  
     * <p>上架或下架指定商品</p>  
     * @param productId 待操作的商品ID
     * @param shopId 待操作的商品所在店铺ID
     * @param enableStatus 最终设定的商品状态，0 下架，1 在前端展。
     * @return  受影响的记录条数
     */  
    int shelveProduct(Long productId, Long shopId, Integer enableStatus);
}
