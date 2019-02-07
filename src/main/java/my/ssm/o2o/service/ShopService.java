package my.ssm.o2o.service;

import java.io.InputStream;

import my.ssm.o2o.entity.Shop;

/**  
 * <p>店铺服务接口</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public interface ShopService {
    /**  
     * <p>注册店铺</p>  
     * @param shop 店铺
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     */  
    void register(Shop shop, InputStream imgIn, String suffix);
    /**  
     * <p>添加或更新店铺缩略图</p>  
     * @param shopId 店铺ID
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     * @return 店铺缩略图的相对路径 
     */  
    String addOrUpdateThumbnail(Integer shopId, InputStream imgIn, String suffix);
}
