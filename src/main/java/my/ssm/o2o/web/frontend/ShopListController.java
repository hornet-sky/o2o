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
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Award;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.AreaService;
import my.ssm.o2o.service.AwardService;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ShopCategoryService;
import my.ssm.o2o.service.ShopService;

/**  
 * <p>店铺列表控制器</p>
 * <p>Date: 2019年2月17日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class ShopListController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/listshopinitinfo")
    @ResponseBody
    public Result listShopInitInfo(@RequestParam(name = "parentShopCategoryId", required = false) Long parentShopCategoryId) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(parentShopCategoryId == null) { //获得全部根类别
                result.put("shopCategoryList", shopCategoryService.findRootCategory());
            } else { //获得指定根类别下的子类别
                result.put("shopCategoryList", shopCategoryService.findSubCategoryByParentId(parentShopCategoryId));
            }
        } catch (Exception e) {
            logger.error("初始化店铺类别失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "初始化店铺类别失败");
        }
        try {
            result.put("areaList", areaService.findAll());
        } catch (Exception e) {
            logger.error("初始化店铺区域失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "初始化店铺区域失败");
        }
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
    
    @GetMapping("/listshop")
    @ResponseBody
    public Result listShop(@RequestParam(name = "parentShopCategoryId", required = false) Long parentShopCategoryId,
            @RequestParam(name = "shopCategoryId", required = false) Long shopCategoryId,
            @RequestParam(name = "areaId", required = false) Integer areaId,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            PagingParams pagingParams) {
        Shop condition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(shopCategoryId);
        if(parentShopCategoryId != null) {
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentShopCategoryId);
            shopCategory.setParent(parentCategory);
        }
        Area area = new Area();
        area.setAreaId(areaId);
        condition.setShopCategory(shopCategory);
        condition.setArea(area);
        condition.setEnableStatus(ShopOperStateEnum.PASS.getState());
        try {
            return shopService.list(condition, searchKey, pagingParams);
        } catch (Exception e) {
            logger.error("获取店铺列表失败", e);
            return new OperationResult<PagingResult<Shop>, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_FAILURE.getState(), "获取店铺列表失败");
        }
    }
    
    @GetMapping("/loadawardlist")
    @ResponseBody
    public Result loadAwardList(@RequestParam(name = "shopId", required = true) Long shopId,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        PagingParams pagingParams = new PagingParams(pageNo, pageSize);
        Award condition = new Award();
        condition.setShopId(shopId);
        condition.setEnableStatus(1);
        try {
            result.put("pagingResult", awardService.listAward(condition, searchKey, pagingParams));
        } catch (Exception e) {
            logger.error("获取奖品列表失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取奖品列表失败");
        }
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
}
