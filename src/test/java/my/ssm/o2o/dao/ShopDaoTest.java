package my.ssm.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.entity.UserInfo;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    @Test
    public void testFindById() {
        Shop shop = shopDao.findById(2L);
        System.out.println(shop);
    }
    
    @Test
    public void testList() {
        Shop condition = new Shop();
        //condition.setShopName("早");
        //condition.setEnableStatus(1);
        PagingParams pagingParams = new PagingParams();
        pagingParams.setPageNo(2);
        pagingParams.setPageSize(2);
        List<Shop> shops = shopDao.list(condition, pagingParams);
        shops.forEach(System.out::println);
    }
    
    @Test
    public void testSave() {
        UserInfo owner = new UserInfo();
        owner.setUserId(1L);
        
        Area area = new Area();
        area.setAreaId(2);
        
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(3L);
        
        Shop shop = new Shop();
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setCreateTime(new Date());
        shop.setAdvice("建议");
        shop.setEnableStatus(0);
        shop.setPhone("13579123456");
        shop.setPriority(1);
        shop.setShopAddr("北京市朝阳区");
        shop.setShopDesc("新店开张");
        shop.setShopImg("店铺缩略图地址");
        shop.setShopName("旺旺奶茶店");
        
        int effectedRows = shopDao.save(shop);
        assertNotNull(shop.getShopId());
        assertEquals(1, effectedRows);
    }
    
    @Test
    public void testUpdate() {
        Area area = new Area();
        area.setAreaId(1);
        
        ShopCategory shopCategory = new ShopCategory();
        
        Shop shop = new Shop();
        shop.setShopId(2L);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setLastEditTime(new Date());
        shop.setAdvice("一点建议~");
        shop.setPhone("13579127");
        shop.setPriority(6);
        //shop.setShopAddr("北京市朝阳区");
        shop.setShopDesc("新店开张，欢迎光临");
        //shop.setShopImg("店铺缩略图地址");
        shop.setShopName("旺仔奶茶店");
        
        int effectedRows = shopDao.update(shop);
        assertEquals(1, effectedRows);
    }
}
