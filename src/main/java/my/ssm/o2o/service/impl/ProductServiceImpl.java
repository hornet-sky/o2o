package my.ssm.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.ProductDao;
import my.ssm.o2o.dao.ProductImgDao;
import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductImg;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.enums.ProductOperStateEnum;
import my.ssm.o2o.exception.ProductOperationException;
import my.ssm.o2o.service.ProductService;
import my.ssm.o2o.util.ImageUtil;
import my.ssm.o2o.util.PathUtil;

/**  
 * <p>商品服务接口实现类</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Override
    public Product findProductById(Long productId, Long shopId) {
        return productDao.findById(productId, shopId);
    }
    @Override
    public Product findOverloadedProductById(Long productId) {
        return productDao.findOverloadedOneById(productId);
    }
    @Override
    public PagingResult<Product> listProduct(Product condition, String searchKey, PagingParams pagingParams) {
        if(pagingParams.isOrderRuleMapEmpty()) { //默认按创建顺序降序排列
            pagingParams.addOrderRule("product_id", Direction.DESC);
        }
        List<Product> list = productDao.list(condition, searchKey, pagingParams);
        long count = productDao.count(condition, searchKey);
        return new PagingResult<Product>(list, count);
    }
    @Transactional
    @Override
    public int shelveProduct(Long productId, Long shopId, Integer enableStatus) {
        Product product = productDao.findById(productId, shopId);
        if(product == null) {
            throw new ProductOperationException("所" + (ProductOperStateEnum.UNSHELVE.getState().equals(enableStatus) ? "下架" : "上架") + "的商品不存在");
        }
        if(product.getProductCategory() == null || product.getProductCategory().getProductCategoryId() == null) {
            throw new ProductOperationException("请先给该商品分类");
        }
        Product condition = new Product();
        condition.setEnableStatus(enableStatus);
        condition.setLastEditTime(new Date());
        condition.setProductId(productId);
        return productDao.update(condition);
    }
    @Transactional
    @Override
    public void addProduct(Product product, ImageHolder coverImg, List<ImageHolder> detailImgs) {
        Long shopId = product.getShop().getShopId();
        //1、保存商品封面图
        String thumbnailRelativePath = null;
        if(coverImg != null) {
            try {
                thumbnailRelativePath = addCoverImg(shopId, coverImg);
                product.setImgAddr(thumbnailRelativePath);
            } catch (Exception e) {
                throw new ProductOperationException("保存商品封面图失败", e);
            }
        }
        //2、保存商品信息
        Date now = new Date();
        product.setCreateTime(now);
        product.setEnableStatus(ProductOperStateEnum.SHELVE.getState());
        try {
            productDao.save(product);
        } catch (Exception e) {
            //删除已保存的封面图
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            throw new ProductOperationException("保存商品信息失败", e);
        }
        //3、保存商品详情图
        if(detailImgs.isEmpty()) {
            return;
        }
        Long productId = product.getProductId();
        List<ProductImg> productDetailImageContainer = new ArrayList<>();
        String detailImgRelativePath = null;
        ProductImg productDetailImage = null;
        try {
            for(int i = 0, len = detailImgs.size(); i < len; i++) {
                detailImgRelativePath = addDetailImg(shopId, detailImgs.get(i));
                productDetailImage = new ProductImg();
                productDetailImage.setCreateTime(now);
                productDetailImage.setImgAddr(detailImgRelativePath);
                productDetailImage.setPriority(len - i);
                productDetailImage.setProductId(productId);
                productDetailImageContainer.add(productDetailImage);
            }
            productImgDao.batchInsert(productDetailImageContainer);
        } catch (Exception e) {
            //删除已保存的封面图
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            //删除已保存的详情图
            for(ProductImg productImg : productDetailImageContainer) {
                ImageUtil.remove(productImg.getImgAddr());
            }
            throw new ProductOperationException("保存商品详情图失败", e);
        }
    }
    @Transactional
    @Override
    public void modifyProduct(Product product, ImageHolder coverImg, List<ImageHolder> detailImgs) {
        Long shopId = product.getShop().getShopId();
        Long productId = product.getProductId();
        //1、更新商品封面图
        String thumbnailRelativePath = null;
        if(coverImg != null) {
            try {
                thumbnailRelativePath = updateCoverImg(shopId, productId, coverImg);
                product.setImgAddr(thumbnailRelativePath);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品封面图失败", e);
            }
        }
        //2、更新商品信息
        Date now = new Date();
        product.setLastEditTime(now);
        try {
            productDao.update(product);
        } catch (Exception e) {
            //删除已更新的缩略图
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            throw new ProductOperationException("更新商品信息失败", e);
        }
        //3、更新商品详情图
        if(detailImgs.isEmpty()) {
            return;
        }
        List<ProductImg> productDetailImageContainer = new ArrayList<>();
        String detailImgRelativePath = null;
        ProductImg productDetailImage = null;
        try {
            //先删除
            delProductDetailImgs(productId);
            //再添加
            for(int i = 0, len = detailImgs.size(); i < len; i++) {
                detailImgRelativePath = addDetailImg(shopId, detailImgs.get(i));
                productDetailImage = new ProductImg();
                productDetailImage.setCreateTime(now);
                productDetailImage.setImgAddr(detailImgRelativePath);
                productDetailImage.setPriority(len - i);
                productDetailImage.setProductId(productId);
                productDetailImageContainer.add(productDetailImage);
            }
            productImgDao.batchInsert(productDetailImageContainer);
        } catch (Exception e) {
            //删除已更新的缩略图
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            //删除已更新的详情图
            for(ProductImg productImg : productDetailImageContainer) {
                ImageUtil.remove(productImg.getImgAddr());
            }
            throw new ProductOperationException("更新商品详情图失败", e);
        }
    }
    @Transactional
    @Override
    public String addCoverImg(Long shopId, ImageHolder image) {
        try {
            return ImageUtil.generateCoverImage(image,
                    PathUtil.getShopImageRelativeDirPath(shopId));
        } catch (IOException e) {
            throw new ProductOperationException(e);
        }
    }
    @Transactional
    @Override
    public String updateCoverImg(Long shopId, Long productId, ImageHolder image) {
        try {
            String oldRelativePath = productDao.findById(productId, shopId).getImgAddr();
            ImageUtil.remove(oldRelativePath);
        } catch (Exception e) {
            throw new ProductOperationException("删除旧的商品封面图失败");
        }
        return addCoverImg(shopId, image);
    }
    @Transactional
    @Override
    public String addDetailImg(Long shopId, ImageHolder image) {
        try {
            return ImageUtil.generateDetailImage(image,
                    PathUtil.getShopImageRelativeDirPath(shopId));
        } catch (IOException e) {
            throw new ProductOperationException(e);
        }
    }
    @Override
    public List<ProductImg> listProductDetailImg(Long productId) {
        ProductImg condition = new ProductImg();
        condition.setProductId(productId);
        return productImgDao.list(condition, null);
    }
    @Transactional
    @Override
    public int delProductDetailImgs(Long productId) {
        if(productId == null || productId <= 0) {
            throw new ProductOperationException("商品ID不正确");
        }
        ProductImg condition = new ProductImg();
        condition.setProductId(productId);
        List<ProductImg> list = productImgDao.list(condition, null);
        //先删除图片
        list.forEach(img -> {
            ImageUtil.remove(img.getImgAddr());
        });
        //再删除记录
        return productImgDao.delete(condition);
    }
}
