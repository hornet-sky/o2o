package my.ssm.o2o.util;

import org.junit.Test;

import my.ssm.o2o.BaseTest;

public class UtilTest extends BaseTest {
    @Test
    public void testImageUtilRemove() {
        ImageUtil.remove("upload/item/shop/8");
    }
    
    @Test
    public void testPathUtil() {
        System.out.println(PathUtil.getImageBaseDirPath());
        System.out.println(PathUtil.getShopImageRelativeDirPath(11));
    }
}
