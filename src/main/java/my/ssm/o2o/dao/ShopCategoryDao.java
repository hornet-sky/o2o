package my.ssm.o2o.dao;

import java.util.List;

import my.ssm.o2o.entity.ShopCategory;

/**  
 * <p>店铺类别数据访问接口</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */  
public interface ShopCategoryDao {
    List<ShopCategory> findByCondition(ShopCategory condition);
    List<ShopCategory> findRoot();
    List<ShopCategory> findAllSub();
}
