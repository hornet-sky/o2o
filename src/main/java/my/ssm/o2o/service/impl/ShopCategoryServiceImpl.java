package my.ssm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ssm.o2o.dao.ShopCategoryDao;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.service.ShopCategoryService;

/**  
 * <p>店铺类别服务接口实现类</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */ 
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> findByCondition(ShopCategory condition) {
        return shopCategoryDao.findByCondition(condition);
    }

    @Override
    public List<ShopCategory> findRootCategory() {
        return shopCategoryDao.findRoot();
    }

    @Override
    public List<ShopCategory> findAllSubCategory() {
        return shopCategoryDao.findAllSub();
    }
    
}
