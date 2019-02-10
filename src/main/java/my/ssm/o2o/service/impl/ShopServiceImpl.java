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
    public void registerShop(Shop shop, InputStream imgIn, String suffix) {
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
        String thumbnailRelativePath = addThumbnail(shopId, imgIn, suffix);
        shop.setShopImg(thumbnailRelativePath);
    }

    @Transactional
    @Override
    public String addThumbnail(Integer shopId, InputStream imgIn, String suffix) {
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
            ImageUtil.remove(thumbnailRelativePath); //删除生成好的缩略图
            throw new ShopOperationException("更新店铺缩略图相对地址失败");
        }
        return thumbnailRelativePath;
    }
    
    @Transactional
    @Override
    public String updateThumbnail(Integer shopId, InputStream imgIn, String suffix) {
        String oldThumbnailRelativePath = null;
        try {
            oldThumbnailRelativePath = shopDao.findById(shopId).getShopImg();
            ImageUtil.remove(oldThumbnailRelativePath);
        } catch (Exception e) {
            logger.error("删除旧的店铺缩略图失败：shopId={}，oldThumbnailRelativePath={}，ShopImgInputStream={}，suffix={}",
                    shopId, oldThumbnailRelativePath, imgIn, suffix);
            throw new ShopOperationException("删除旧的店铺缩略图失败");
        }
        return addThumbnail(shopId, imgIn, suffix);
    }

    @Override
    public Shop findShopById(Integer shopId) {
        return shopDao.findById(shopId);
    }

    @Transactional
    @Override
    public void modifyShop(Shop shop, InputStream imgIn, String suffix) {
        //1、修改店铺信息
        shop.setLastEditTime(new Date());
        int effectedRows = shopDao.update(shop);
        if(effectedRows == 0) {
            throw new ShopOperationException("修改店铺信息失败");
        }
        //2、更新店铺缩略图
        Integer shopId = shop.getShopId();
        if(imgIn == null || StringUtils.isBlank(suffix)) {
            logger.info("未更新店铺缩略图：shopId={}，shopName={}，ShopImgInputStream={}，suffix={}",
                    shopId, shop.getShopName(), imgIn, suffix);
            return;
        }
        String thumbnailRelativePath = updateThumbnail(shopId, imgIn, suffix);
        shop.setShopImg(thumbnailRelativePath);
    }
}