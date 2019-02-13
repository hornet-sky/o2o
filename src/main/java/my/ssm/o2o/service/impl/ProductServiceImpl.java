package my.ssm.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.ProductDao;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.enums.ProductOperStateEnum;
import my.ssm.o2o.exception.ProductOperationException;
import my.ssm.o2o.service.ProductService;

/**  
 * <p>商品服务接口实现类</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product findProductById(Long productId) {
        return productDao.findById(productId);
    }
    @Override
    public PagingResult<Product> listProduct(Product condition, PagingParams pagingParams) {
        if(pagingParams.isOrderRuleMapEmpty()) { //默认按创建顺序降序排列
            pagingParams.addOrderRule("product_id", Direction.DESC);
        }
        List<Product> list = productDao.list(condition, pagingParams);
        long count = productDao.count(condition);
        return new PagingResult<Product>(list, count);
    }
    @Transactional
    @Override
    public int shelveProduct(Long productId, Long shopId, Integer enableStatus) {
        Shop shop = new Shop();
        shop.setShopId(shopId);
        Product condition = new Product();
        condition.setShop(shop);
        condition.setProductId(productId);
        if(productDao.count(condition) == 0) {
            throw new ProductOperationException("所" + (ProductOperStateEnum.UNSHELVE.getState().equals(enableStatus) ? "下架" : "上架") + "的商品不存在");
        }
        Product product = new Product();
        product.setEnableStatus(enableStatus);
        product.setLastEditTime(new Date());
        product.setProductId(productId);
        return productDao.update(product);
    }
}
