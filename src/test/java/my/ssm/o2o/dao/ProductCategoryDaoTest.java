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
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.enums.Direction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //使测试方法按指定顺序执行
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testBListAndCount() {
        ProductCategory condition = new ProductCategory();
        condition.setShopId(14L);
        condition.setProductCategoryName("草莓");
        PagingParams pagingParams = new PagingParams(1, 2, "product_category_id", Direction.DESC);
        List<ProductCategory> list = productCategoryDao.list(condition, pagingParams);
        list.forEach(System.out::println);
    }
    
    @Test
    public void testABatchInsert() {
        List<ProductCategory> list = new ArrayList<>();
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryName("芒果奶茶");
        pc1.setPriority(1);
        pc1.setCreateTime(new Date());
        pc1.setShopId(15L);
        list.add(pc1);
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryName("柠檬奶茶");
        pc2.setPriority(1);
        pc2.setCreateTime(new Date());
        pc2.setShopId(15L);
        list.add(pc2);
        
        int r = productCategoryDao.batchInsert(list);
        System.out.println(r);
        list.forEach(System.out::println);
    }
    
    @Test
    public void testCDelete() {
        ProductCategory condition = null;
        List<ProductCategory> list = productCategoryDao.list(condition, null);
        list.forEach(pc -> {
            if("芒果奶茶".equals(pc.getProductCategoryName())) {
                productCategoryDao.delete(pc);
            }
        });
    }
}
