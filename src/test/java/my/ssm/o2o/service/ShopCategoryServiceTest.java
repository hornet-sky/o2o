package my.ssm.o2o.service;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.entity.ShopCategory;

public class ShopCategoryServiceTest extends BaseTest {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Test
    public void testRegister() throws IOException {
        List<ShopCategory> categorys = shopCategoryService.findSubCategoryByParentId(1L);
        categorys.forEach(System.out::println);
    }
}
