package my.ssm.o2o.dao;

import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import my.ssm.o2o.BaseTest;
import my.ssm.o2o.dao.cache.CacheDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CacheDaoTest extends BaseTest {
    @Autowired
    private CacheDao cacheDao;
    @Test
    public void testASet() {
        String r = cacheDao.set("arealist3", "[{\"areaId\": 3, \"areaName\": \"东苑\"}, {\"areaId\": 2, \"areaName\": \"北苑\"}]");
        System.out.println("set -> " + r);
    }
    
    @Test
    public void testEDel() {
        Long l = cacheDao.del("arealist", "arealist2", "arealist3");
        System.out.println("del -> " + l);
    }
    
    @Test
    public void testBExists() {
        boolean r = cacheDao.exists("arealist");
        System.out.println("exists -> " + r);
    }
    
    @Test
    public void testCKeys() {
        Set<String> keys = cacheDao.keys("area");
        System.out.println("keys -> " + keys);
    }
    
    @Test
    public void testDVal() {
        String val = cacheDao.val("arealist");
        System.out.println("val -> " + val);
    }
}
