package my.ssm.o2o.web.shopadmin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.enums.ProductOperStateEnum;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ProductCategoryService;
import my.ssm.o2o.service.ProductService;
import my.ssm.o2o.util.CommonUtil;
import my.ssm.o2o.util.KaptchaUtil;

/**  
 * <p>商品管理功能控制器</p>
 * <p>Date: 2019年2月11日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/getproductoperationinitdata")
    @ResponseBody
    public Result getProductOperationInitData(
            @RequestParam(name = "productId", required = false) Long productId,
            @RequestParam(name = "shopId") Long shopId,
            HttpSession session) {
        logger.debug("productId={}, shopId={}", productId, shopId);
        //验证是否属于当前登录用户
        UserInfo owner = (UserInfo) session.getAttribute("user");
        Shop currShop = (Shop) session.getAttribute("currShop");
        if(!shopId.equals(currShop.getShopId())) {
            logger.error("getproductoperationinitdata - 非法操作：用户[{} - {}] 请求店铺ID[{}] 缓存店铺ID[{}]",
                    owner.getUserId(), owner.getName(), shopId, currShop.getShopId());
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.ILLEGAL_OPERATION);
        }
        Map<String, Object> data = new HashMap<>();
        if(productId != null) {
            Product product = productService.findProductById(productId);
            if(product == null) {
                return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.PRODUCT_NOT_FOUND, productId.toString());
            }
            data.put("product", product);
        }
        ProductCategory condition = new ProductCategory();
        condition.setShopId(shopId);
        PagingParams pagingParams = new PagingParams(null, null, "priority", Direction.DESC);
        try {
            data.put("productCategoryList", productCategoryService.listProductCategory(condition, pagingParams).getRows());
            data.put("imageUploadProps", commonService.getImageUploadProps());
            return new OperationResult<Map<String, Object>, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_SUCCESS, data);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Map<String, Object>, ProductOperStateEnum>(ProductOperStateEnum.INITIALIZATION_FAILURE, e);
        }
    }
    
    @PostMapping("/addormodifyproduct")
    @ResponseBody
    public Result addOrModifyProduct(
            @RequestParam(name = "productId", required = false) Long productId, 
            @RequestParam(name = "productName") String productName, 
            @RequestParam(name = "productCategory") Long productCategory,
            @RequestParam(name = "priority") Integer priority,
            @RequestParam(name = "normalPrice", required = false) String normalPrice,
            @RequestParam(name = "promotionPrice", required = false) String promotionPrice,
            @RequestParam(name = "rewardsPoints", required = false) String rewardsPoints,
            @RequestParam(name = "productDesc", required = false) String productDesc,
            @RequestParam(name = "verifyCodeActual") String verifyCodeActual,
            @RequestParam(name = "coverImg", required = false) CommonsMultipartFile coverImg,
            @RequestParam(name = "detailImgs", required = false) CommonsMultipartFile[] detailImgs,
            HttpSession session) {
        logger.debug("addOrModifyProduct - productId={}, productName={}, productCategory={}, priority={}, normalPrice={}, promotionPrice={}, rewardsPoints={}, productDesc={}, verifyCodeActual={}, coverImg={}, detailImgs={}",
                productId, productName, productCategory, priority, normalPrice, promotionPrice, rewardsPoints, productDesc, verifyCodeActual, coverImg, detailImgs == null ? null : Arrays.toString(detailImgs));
        if(!KaptchaUtil.checkVerifyCode(verifyCodeActual, session)) {
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.INVALID_VERIFY_CODE);
        }
        Map<String, Object> imageUploadProps = commonService.getImageUploadProps();
        
        ImageHolder image = null;
        /*if(coverImg != null) {
            image = new ImageHolder(coverImg);
            String[] acceptImageTypes = (String[]) commonService.getImageUploadProps().get("acceptImageTypes");
            //验证上传的照片格式是否合法
            if(!CommonUtil.isAcceptedMimeType("image", image.getSuffix().substring(1), acceptImageTypes)) {
                return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INVALID_IMAGE_TYPE, image.getSuffix());
            }
        }*/
        
        //TODO 把currShop设置进product里
        
        
        
        
        //TODO 配置一个最大上传文件个数，应用到前端js和后台
        
        
        Integer maxImageCount = (Integer) imageUploadProps.get("maxImageCount");
        return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_SUCCESS);
    }
    
    @GetMapping("/getproductlist")
    @ResponseBody
    public Result getProductList(@RequestParam("shopId") Long shopId,
            PagingParams pagingParams, HttpSession session) { //TODO 实现前端分页、筛查
        Shop currShop = (Shop) session.getAttribute("currShop");
        UserInfo owner = (UserInfo) session.getAttribute("user");
        if(currShop == null || !currShop.getShopId().equals(shopId)) {
            logger.error("getproductlist - 非法操作：用户[{} - {}] 请求店铺ID[{}] 缓存店铺ID[{}]", 
                    owner.getUserId(), owner.getName(), shopId, currShop == null ? null : currShop.getShopId());
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.ILLEGAL_OPERATION);
        }
        Product condition = new Product();
        condition.setShop(currShop);
        try {
            return productService.listProduct(condition , pagingParams);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/shelveproduct")
    @ResponseBody
    public Result shelveProduct(@RequestParam("productId") Long productId, 
            @RequestParam("enableStatus") Integer enableStatus, HttpSession session) {
        Shop currShop = (Shop) session.getAttribute("currShop");
        try {
            int effectedRows = productService.shelveProduct(productId, currShop.getShopId(), enableStatus);
            if(effectedRows > 0) {
                return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_SUCCESS);
            }
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/getproductcategorylist")
    @ResponseBody
    public Result getProductCategoryList(@RequestParam("shopId") Long shopId,
            PagingParams pagingParams, HttpSession session) { //TODO 实现前端分页、筛查
        Shop currShop = (Shop) session.getAttribute("currShop");
        UserInfo owner = (UserInfo) session.getAttribute("user");
        if(currShop == null || !currShop.getShopId().equals(shopId)) {
            logger.error("getproductcategorylist - 非法操作：用户[{} - {}] 请求店铺ID[{}] 缓存店铺ID[{}]", 
                    owner.getUserId(), owner.getName(), shopId, currShop == null ? null : currShop.getShopId());
            return new OperationResult<Product, ProductOperStateEnum>(ProductOperStateEnum.ILLEGAL_OPERATION);
        }
        ProductCategory condition = new ProductCategory();
        condition.setShopId(shopId);
        try {
            return productCategoryService.listProductCategory(condition , pagingParams);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/delproductcategory")
    @ResponseBody
    public Result delProductCategory(@RequestParam("productCategoryId") Long productCategoryId, 
            HttpSession session) {
        Shop currShop = (Shop) session.getAttribute("currShop");
        ProductCategory condition = new ProductCategory();
        condition.setProductCategoryId(productCategoryId);
        condition.setShopId(currShop.getShopId());
        try {
            int effectedRows = productCategoryService.delProductCategory(condition);
            if(effectedRows == 1) {
                return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_SUCCESS);
            }
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @PostMapping("/batchinsertproductcategory")
    @ResponseBody
    public Result batchInsertProductCategory(@RequestBody List<ProductCategory> productCategoryList, 
            HttpSession session) {
        if(productCategoryList == null || productCategoryList.isEmpty()) {
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.EMPTY_PRODUCT_CATEGORY);
        }
        Shop currShop = (Shop) session.getAttribute("currShop");
        Date now = new Date();
        productCategoryList.forEach(pc -> {
            pc.setShopId(currShop.getShopId());
            pc.setCreateTime(now);
        });
        try {
            int n = productCategoryService.batchInsertProductCategory(productCategoryList);
            if(n > 0) {
                return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_SUCCESS);
            }
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<ProductCategory, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE, e);
        }
    }
}
