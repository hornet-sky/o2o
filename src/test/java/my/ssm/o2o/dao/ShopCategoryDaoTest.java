package my.ssm.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testFindByCondition() {
        ShopCategory condition = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1L);
        condition.setParent(parent);
        
        List<ShopCategory> shopCategorys = shopCategoryDao.findByCondition(condition);
        shopCategorys.forEach(System.out::println);
    }
}
