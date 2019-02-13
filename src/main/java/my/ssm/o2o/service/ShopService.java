package my.ssm.o2o.service;

import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Shop;

/**  
 * <p>店铺服务接口</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public interface ShopService {
    /**  
     * <p>查找指定ID的店铺信息</p>  
     * @param shopId 店铺ID
     * @return  店铺信息
     */  
    Shop findShopById(Long shopId);
    /**  
     * <p>查找指定条件及分页参数的店铺信息</p>  
     * @param condition 查询条件
     * @param pagingParams 分页参数
     * @return  查询结果
     */  
    PagingResult<Shop> list(Shop condition, PagingParams pagingParams);
    /**  
     * <p>注册店铺</p>  
     * @param shop 店铺
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     */  
    void registerShop(Shop shop, ImageHolder image);
    /**  
     * <p>修改店铺信息</p>  
     * @param shop 店铺
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     */  
    void modifyShop(Shop shop, ImageHolder image);
    /**  
     * <p>添加店铺缩略图</p>  
     * @param shopId 店铺ID
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     * @return 店铺缩略图的相对路径 
     */  
    String addThumbnail(Long shopId, ImageHolder image);
    /**  
     * <p>更新店铺缩略图</p>  
     * @param shopId 店铺ID
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     * @return 新的店铺缩略图的相对路径 
     */  
    String updateThumbnail(Long shopId, ImageHolder image);
}
