package my.ssm.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Test
    public void testASave() {
        //7 15
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setImgAddr("缩略图的图片");
        product.setNormalPrice("3.00");
        product.setPriority(1);
        product.setProductDesc("这是一杯神奇的柠檬奶茶~");
        product.setProductName("冰点柠檬奶茶");
        product.setPromotionPrice("2.5");
        
        Shop shop = new Shop();
        shop.setShopId(15L);
        product.setShop(shop);
        
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(7L);
        product.setProductCategory(pc);
        
        //-----------
        
        Product product2 = new Product();
        product2.setCreateTime(new Date());
        product2.setEnableStatus(0);
        product2.setImgAddr("缩略图的图片2");
        product2.setNormalPrice("6.00");
        product2.setPriority(2);
        product2.setProductDesc("神奇的鸡蛋灌饼~");
        product2.setProductName("鸡蛋灌饼");
        product2.setPromotionPrice("5.5");
        
        Shop shop2 = new Shop();
        shop2.setShopId(15L);
        product2.setShop(shop2);
        
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryId(5L);
        product2.setProductCategory(pc2);
        
        //-----------
        
        Product product3 = new Product();
        product3.setCreateTime(new Date());
        product3.setEnableStatus(0);
        product3.setImgAddr("缩略图的图片3");
        product3.setNormalPrice("8.00");
        product3.setPriority(1);
        product3.setProductDesc("神奇的珍珠奶茶~");
        product3.setProductName("温珍珠奶茶");
        product3.setPromotionPrice("6.5");
        
        Shop shop3 = new Shop();
        shop3.setShopId(14L);
        product3.setShop(shop3);
        
        ProductCategory pc3 = new ProductCategory();
        pc3.setProductCategoryId(2L);
        product3.setProductCategory(pc3);
        
        productDao.save(product);
        productDao.save(product2);
        productDao.save(product3);
    }
    
    @Test
    public void testBList() {
        Shop shop = new Shop();
        shop.setShopId(15L);
        Product condition = new Product();
        condition.setShop(shop);
        PagingParams pagingParams = null;
        List<Product> list = productDao.list(condition, null, pagingParams);
        list.forEach(System.out::println);
    }
}
