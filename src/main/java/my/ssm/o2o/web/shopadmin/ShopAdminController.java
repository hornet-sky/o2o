package my.ssm.o2o.web.shopadmin;

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
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.AreaService;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ShopCategoryService;
import my.ssm.o2o.service.ShopService;

/**  
 * <p>店铺管理控制器</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
    private Logger logger = LoggerFactory.getLogger(ShopAdminController.class);
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/shopoperation")
    public String shopRegister() {
        return "shop/shopoperation";
    }
    
    @GetMapping("/getinitdata")
    @ResponseBody
    public Result getInitData(
            @RequestParam(name = "parentShopCategoryId") Long parentShopCategoryId,
            @RequestParam(name = "shopId", required = false) Integer shopId) {
        logger.debug("parentShopCategoryId={}, shopId={}", parentShopCategoryId, shopId);
        Map<String, Object> data = new HashMap<>();
        if(shopId != null) {
            Shop shop = shopService.findShopById(shopId);
            if(shop == null) {
                return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.SHOP_NOT_FOUND, shopId.toString());
            }
            data.put("shop", shop);
        }
        ShopCategory condition = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(parentShopCategoryId);
        condition.setParent(parent);
        try {
            data.put("shopCategoryList", shopCategoryService.findByCondition(condition));
            data.put("areaList", areaService.findAll());
            data.put("imageUploadProps", commonService.getImageUploadProps());
            return new OperationResult<Map<String, Object>, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_SUCCESS, data);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INITIALIZATION_FAILURE, e);
        }
    }
}
