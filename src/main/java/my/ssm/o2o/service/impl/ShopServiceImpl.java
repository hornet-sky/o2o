package my.ssm.o2o.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.ShopDao;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.exception.ShopOperationException;
import my.ssm.o2o.service.ShopService;
import my.ssm.o2o.util.ImageUtil;
import my.ssm.o2o.util.PathUtil;

/**  
 * <p>店铺服务接口实现类</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
@Service
public class ShopServiceImpl implements ShopService {
    private Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
    @Autowired
    private ShopDao shopDao;
    
    @Transactional
    @Override
    public void register(Shop shop, InputStream imgIn, String suffix) {
        //1、保存店铺实体
        //把参数校验都放在controller层。为保证运行效率，service层不进行二次校验。
        shop.setPriority(0); //TODO 找一种处理权重的方式
        shop.setEnableStatus(ShopOperStateEnum.CHECKING.getState());
        shop.setCreateTime(new Date());
        int effectedRows = shopDao.save(shop);
        if(effectedRows == 0) {
            throw new ShopOperationException("注册店铺失败");
        }
        //2、添加店铺缩略图
        Integer shopId = shop.getShopId();
        if(shopId == null || shopId <= 0) {
            throw new ShopOperationException("注册店铺失败，未生成有效的店铺ID：" + shopId);
        }
        if(imgIn == null || StringUtils.isBlank(suffix)) {
            logger.info("未添加店铺缩略图：shopId={}，shopName={}，ShopImgInputStream={}，suffix={}",
                    shopId, shop.getShopName(), imgIn, suffix);
            return;
        }
        String thumbnailRelativePath = addOrUpdateThumbnail(shopId, imgIn, suffix);
        shop.setShopImg(thumbnailRelativePath);
    }

    @Transactional
    @Override
    public String addOrUpdateThumbnail(Integer shopId, InputStream imgIn, String suffix) {
        String thumbnailRelativePath = null;
        try {
            thumbnailRelativePath = ImageUtil.generateThumbnail(imgIn, suffix,
                    PathUtil.getShopImageDirRelativePath(shopId));
        } catch (IOException e) {
            throw new ShopOperationException(e);
        }
        Shop shop = new Shop();
        shop.setShopId(shopId);
        shop.setShopImg(thumbnailRelativePath);
        int effectedRows = shopDao.update(shop);
        if(effectedRows == 0) {
            throw new ShopOperationException("添加或更新店铺缩略图失败");
        }
        return thumbnailRelativePath;
    }
}
