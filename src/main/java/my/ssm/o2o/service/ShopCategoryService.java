package my.ssm.o2o.service;

import java.util.List;

import my.ssm.o2o.entity.ShopCategory;

/**  
 * <p>店铺类别服务接口</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */  
public interface ShopCategoryService {
    /**  
     * <p>根据条件查询店铺类别列表</p>  
     * @param condition 条件
     * @return  店铺类别列表
     */  
    List<ShopCategory> findByCondition(ShopCategory condition);
    /**  
     * <p>查询所有根类别</p>  
     * @return  店铺根类别列表
     */  
    List<ShopCategory> findRootCategory();
    /**  
     * <p>查询所有子类别</p>  
     * @return  店铺子类别列表
     */  
    List<ShopCategory> findAllSubCategory();
}
