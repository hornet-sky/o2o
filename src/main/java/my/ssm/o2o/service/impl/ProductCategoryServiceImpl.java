package my.ssm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.ProductCategoryDao;
import my.ssm.o2o.dao.ProductDao;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.service.ProductCategoryService;

/**  
 * <p>商品类别服务接口实现类</p>
 * <p>Date: 2019年2月11日</p>
 * @author Wanghui    
 */  
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public PagingResult<ProductCategory> listProductCategory(ProductCategory condition, PagingParams pagingParams) {
        if(pagingParams.isOrderRuleMapEmpty()) { //默认按创建顺序降序排列
            pagingParams.addOrderRule("product_category_id", Direction.DESC);
        }
        List<ProductCategory> list = productCategoryDao.list(condition, pagingParams);
        long count = productCategoryDao.count(condition);
        return new PagingResult<ProductCategory>(list, count);
    }
    @Transactional
    @Override
    public int delProductCategory(ProductCategory condition) {
        //先将该类别下的商品的外键置为null
        productDao.updateProductCategoryToNull(condition.getProductCategoryId());
        //然后再删除该类别
        return productCategoryDao.delete(condition);
    }
    @Transactional
    @Override
    public int batchInsertProductCategory(List<ProductCategory> productCategoryList) {
        return productCategoryDao.batchInsert(productCategoryList);
    }
}
