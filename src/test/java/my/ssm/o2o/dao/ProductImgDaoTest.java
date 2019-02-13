package my.ssm.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void testBList() {
        ProductImg condition = new ProductImg();
        condition.setProductId(7L);
        PagingParams pagingParams = null;
        List<ProductImg> list = productImgDao.list(condition, pagingParams);
        list.forEach(System.out::println);
    }
    
    @Test
    public void testABatchInsert() {
        List<ProductImg> list = new ArrayList<>();
        ProductImg productImg = new ProductImg();
        productImg.setCreateTime(new Date());
        productImg.setImgAddr("商品图片地址");
        productImg.setImgDesc("商品图片说明");
        productImg.setPriority(1);
        productImg.setProductId(7L);
        
        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddr("商品图片地址2");
        productImg2.setImgDesc("商品图片说明2");
        productImg2.setPriority(2);
        productImg2.setProductId(7L);
        
        list.add(productImg);
        list.add(productImg2);
        productImgDao.batchInsert(list);
    }
    
    @Test
    public void testCDelete() {
        ProductImg condition = new ProductImg();
        condition.setProductId(7L);
        productImgDao.delete(condition);
    }
}
