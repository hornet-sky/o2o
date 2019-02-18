package my.ssm.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Product;
import my.ssm.o2o.entity.ProductCategory;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.enums.ProductOperStateEnum;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ProductCategoryService;
import my.ssm.o2o.service.ProductService;
import my.ssm.o2o.service.ShopService;

/**  
 * <p>商品列表控制器</p>
 * <p>Date: 2019年2月18日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class ProductListController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/listproductinitinfo")
    @ResponseBody
    public Result listProductInitInfo(@RequestParam(name = "shopId") Long shopId) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("shop", shopService.findShopById(shopId));
        } catch (Exception e) {
            logger.error("获取店铺详情失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取店铺详情失败");
        }
        try {
            ProductCategory condition = new ProductCategory();
            condition.setShopId(shopId);
            result.put("productCategoryList", productCategoryService.listProductCategory(condition));
        } catch (Exception e) {
            logger.error("获取商品类别列表失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取商品类别列表失败");
        }
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
    
    @GetMapping("/listproduct")
    @ResponseBody
    public Result listProduct(@RequestParam(name = "shopId") Long shopId,
            @RequestParam(name = "productCategoryId", required = false) Long productCategoryId,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            PagingParams pagingParams) {
        Shop shop = new Shop();
        shop.setShopId(shopId);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(productCategoryId);
        Product condition = new Product();
        condition.setShop(shop);
        condition.setProductCategory(productCategory);
        condition.setEnableStatus(ProductOperStateEnum.SHELVE.getState()); //上架商品
        pagingParams.addOrderRule("priority", Direction.DESC); //按优先级降序排列
        try {
            return productService.listProduct(condition, searchKey, pagingParams);
        } catch (Exception e) {
            logger.error("获取商品列表失败", e);
            return new OperationResult<PagingResult<Product>, ProductOperStateEnum>(ProductOperStateEnum.OPERATION_FAILURE.getState(), "获取商品列表失败");
        }
    }
}
