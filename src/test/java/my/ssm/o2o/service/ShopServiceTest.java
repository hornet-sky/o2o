package my.ssm.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.entity.UserInfo;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void testRegister() throws IOException {
        UserInfo owner = new UserInfo();
        owner.setUserId(1L);
        
        Area area = new Area();
        area.setAreaId(2);
        
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        
        Shop shop = new Shop();
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        //shop.setCreateTime(new Date());
        shop.setAdvice("一点建议");
        //shop.setEnableStatus(0);
        shop.setPhone("13579123456");
        shop.setPriority(2);
        shop.setShopAddr("上海黄浦区南京东路20号");
        shop.setShopDesc("新店开张");
        shop.setShopName("和平饭店22");
        
        File shopImg = new File("C:\\Users\\Wang\\Desktop\\xiaohuangren.jpg");
        ImageHolder image = new ImageHolder(new FileInputStream(shopImg), ".jpg");
        shopService.registerShop(shop, image); 
    }
}
