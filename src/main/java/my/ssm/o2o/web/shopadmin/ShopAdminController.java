package my.ssm.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
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
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.AreaService;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ShopCategoryService;

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
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/shopregister")
    public String shopRegister() {
        return "shop/shopregister";
    }
    
    @GetMapping("/getshopregisterinitinfo")
    @ResponseBody
    public Result getShopRegisterInitInfo(@RequestParam("parentShopCategoryId") Long parentShopCategoryId) {
        logger.debug("parentShopCategoryId={}", parentShopCategoryId);
        ShopCategory condition = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(parentShopCategoryId);
        condition.setParent(parent);
        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.findByCondition(condition);
            List<Area> areaList = areaService.findAll();
            Map<String, Object> imageUploadProps = commonService.getImageUploadProps();
            Map<String, Object> data = new HashMap<>();
            data.put("shopCategoryList", shopCategoryList);
            data.put("areaList", areaList);
            data.put("imageUploadProps", imageUploadProps);
            return new OperationResult<Map<String, Object>, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_SUCCESS, data);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INITIALIZATION_FAILURE, e);
        }
    }
}
