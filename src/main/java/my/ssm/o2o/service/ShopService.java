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
     * <p>查找指定ID的店铺信息</p>  
     * @param shopId 店铺ID
     * @return  店铺信息
     */  
    Shop findShopById(Integer shopId);
    /**  
     * <p>注册店铺</p>  
     * @param shop 店铺
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     */  
    void registerShop(Shop shop, InputStream imgIn, String suffix);
    /**  
     * <p>修改店铺信息</p>  
     * @param shop 店铺
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     */  
    void modifyShop(Shop shop, InputStream imgIn, String suffix);
    /**  
     * <p>添加店铺缩略图</p>  
     * @param shopId 店铺ID
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     * @return 店铺缩略图的相对路径 
     */  
    String addThumbnail(Integer shopId, InputStream imgIn, String suffix);
    /**  
     * <p>更新店铺缩略图</p>  
     * @param shopId 店铺ID
     * @param imgIn  店铺照片输入流
     * @param suffix 店铺照片后缀名，例如“.jpg”
     * @return 新的店铺缩略图的相对路径 
     */  
    String updateThumbnail(Integer shopId, InputStream imgIn, String suffix);
}