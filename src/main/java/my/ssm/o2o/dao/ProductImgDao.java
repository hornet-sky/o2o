package my.ssm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.ProductImg;

/**  
 * <p>商品详情图片数据访问接口</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
public interface ProductImgDao {
    /**  
     * <p>查找指定条件及分页参数的商品详情图片</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  商品详情图片列表
     */  
    List<ProductImg> list(@Param("condition") ProductImg condition, @Param("pagingParams") PagingParams pagingParams);
    /**  
     * <p>删除指定条件的商品图片信息</p>  
     * @param condition 条件
     * @return  受影响的记录数
     */  
    int delete(@Param("condition") ProductImg condition);
    /**  
     * <p>批量插入商品图片</p>  
     * @param productImgList 商品图片列表
     * @return  受影响的记录数
     */  
    int batchInsert(@Param("productImgList") List<ProductImg> productImgList);
}
