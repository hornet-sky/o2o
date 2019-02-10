package my.ssm.o2o.dao;

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
    Shop findById(Integer shopId);
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
