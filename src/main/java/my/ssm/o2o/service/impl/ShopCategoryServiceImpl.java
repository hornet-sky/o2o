package my.ssm.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.ssm.o2o.dao.ShopCategoryDao;
import my.ssm.o2o.dao.cache.CacheDao;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.exception.AreaException;
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
    @Autowired
    private CacheDao cacheDao;

    @Override
    public List<ShopCategory> findByCondition(ShopCategory condition) {
        return shopCategoryDao.findByCondition(condition);
    }

    @Override
    public List<ShopCategory> findRootCategory() {
        String key = ROOT_SHOP_CATEGORY_LIST;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(cacheDao.exists(key)) {
                String val = cacheDao.val(key);
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                return mapper.readValue(val, type);
            }
            List<ShopCategory> rootList = shopCategoryDao.findRoot();
            String rootListJson = mapper.writeValueAsString(rootList);
            cacheDao.set(key, rootListJson);
            return rootList;
        } catch (Exception e) {
            throw new AreaException("查询全部店铺大类别期间产生异常", e);
        }
    }

    @Override
    public List<ShopCategory> findAllSubCategory() {
        String key = SUB_SHOP_CATEGORY_LIST;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(cacheDao.exists(key)) {
                String val = cacheDao.val(key);
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                return mapper.readValue(val, type);
            }
            List<ShopCategory> allSubList = shopCategoryDao.findAllSub();
            String allSubListJson = mapper.writeValueAsString(allSubList);
            cacheDao.set(key, allSubListJson);
            return allSubList;
        } catch (Exception e) {
            throw new AreaException("查询全部店铺子类别期间产生异常", e);
        }
    }

    @Override
    public List<ShopCategory> findSubCategoryByParentId(Long parentShopCategoryId) {
        String key = SUB_SHOP_CATEGORY_LIST + "_parent" + parentShopCategoryId;
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(parentShopCategoryId);
        ShopCategory condition = new ShopCategory();
        condition.setParent(parent);
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(cacheDao.exists(key)) {
                String val = cacheDao.val(key);
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                return mapper.readValue(val, type);
            }
            List<ShopCategory> shopCategoryList = findByCondition(condition);
            String shopCategoryListJson = mapper.writeValueAsString(shopCategoryList);
            cacheDao.set(key, 60, shopCategoryListJson);
            return shopCategoryList;
        } catch (Exception e) {
            throw new AreaException("查询指定大类别下的店铺子类别期间产生异常", e);
        }
    }
}
