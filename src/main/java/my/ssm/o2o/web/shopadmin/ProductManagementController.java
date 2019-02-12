package my.ssm.o2o.web.shopadmin;

import java.util.Date;
import java.util.List;

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

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.ProductOperStateEnum;
import my.ssm.o2o.service.ProductCategoryService;

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
