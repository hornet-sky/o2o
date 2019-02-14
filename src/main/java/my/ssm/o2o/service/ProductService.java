package my.ssm.o2o.service;

import java.util.List;

import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductImg;

/**  
 * <p>商品服务接口</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
public interface ProductService {
    /**  
     * <p>查询指定ID的商品信息</p>  
     * @param productId 商品ID
     * @return  商品信息
     */  
    Product findProductById(Long productId);
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
    /**  
     * <p>添加商品</p>  
     * @param product 待添加的商品
     * @param coverImg 商品封面图
     * @param detailImgs 商品详情图列表
     */  
    void addProduct(Product product, ImageHolder coverImg, List<ImageHolder> detailImgs);
    /**  
     * <p>修改商品</p>  
     * @param product 待修改的商品
     * @param coverImg 商品封面图
     * @param detailImgs 商品详情图列表
     */  
    void modifyProduct(Product product, ImageHolder coverImg, List<ImageHolder> detailImgs);
    /**  
     * <p>添加商品封面图（缩略图）</p>  
     * @param shopId 店铺ID，主要用于生成存放图片的目录
     * @param image 待添加的封面图
     * @return 商品封面图的相对路径 
     */  
    String addCoverImg(Long shopId, ImageHolder image);
    /**  
     * <p>更新商品封面图（缩略图）</p>  
     * @param shopId 店铺ID，主要用于生成存放图片的目录
     * @param oldRelativePath 旧的封面图相对路径
     * @param image 新的封面图
     * @return 新的封面图相对路径
     */  
    String updateCoverImg(Long shopId, String oldRelativePath, ImageHolder image);
    /**  
     * <p>添加商品详情图</p>  
     * @param shopId 店铺ID，主要用于生成存放图片的目录
     * @param image 待添加的详情图
     * @return 商品详情图的相对路径 
     */  
    String addDetailImg(Long shopId, ImageHolder image);
    /**  
     * <p>查询指定商品的所有详情图</p>  
     * @param productId 商品ID
     * @return  指定商品的所有详情图
     */  
    List<ProductImg> listProductDetailImg(Long productId);
    /**  
     * <p>删除指定商品的所有详情图</p>  
     * @param productId 商品ID
     * @return  受影响的记录数
     */  
    int delProductDetailImgs(Long productId);
}
