package my.ssm.o2o.web.shopadmin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.AreaService;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ShopCategoryService;
import my.ssm.o2o.service.ShopService;
import my.ssm.o2o.util.CommonUtil;
import my.ssm.o2o.util.KaptchaUtil;

/**  
 * <p>店铺管理功能控制器</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */ 
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    private Logger logger = LoggerFactory.getLogger(ShopManagementController.class);
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/getshoplistinitdata")
    @ResponseBody
    public Result getShopListInitData(HttpSession session) {
        try {
            //TODO 先在session里放一个user用于测试，最后别忘了去掉
            UserInfo user = new UserInfo();
            user.setUserId(1L);
            user.setName("Jack");
            session.setAttribute("user", user);
            //---- end ----
            UserInfo owner = (UserInfo) session.getAttribute("user");
            return new OperationResult<UserInfo, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_SUCCESS, owner);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<UserInfo, ShopOperStateEnum>(ShopOperStateEnum.INITIALIZATION_FAILURE, e);
        }
    }
    
    @GetMapping("/getshoplist")
    @ResponseBody
    public Result getShopList(PagingParams pagingParams, HttpSession session) {
        try {
            //TODO 实现前端分页以及按条件查询
            UserInfo owner = (UserInfo) session.getAttribute("user");
            Shop condition = new Shop();
            condition.setOwner(owner);
            return shopService.list(condition, pagingParams);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<UserInfo, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/getshopmanagementinitdata")
    @ResponseBody
    public Result getShopManagementInitData(@RequestParam(name = "shopId") Long shopId, 
            HttpSession session) {
        Shop shop = (Shop) session.getAttribute("currShop");
        if(shop == null || !shopId.equals(shop.getShopId())) {
            UserInfo owner = (UserInfo) session.getAttribute("user");
            logger.error("getshopmanagementinitdata - 非法操作：用户[{} - {}] 请求店铺[{} - {}]",
                    owner.getUserId(), owner.getName(), shopId, shop == null ? null : shop.getShopName());
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.ILLEGAL_OPERATION);
        }
        return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_SUCCESS, shop);
    }
    
    @GetMapping("/getshopoperationinitdata")
    @ResponseBody
    public Result getShopOperationInitData(
            @RequestParam(name = "parentShopCategoryId") Long parentShopCategoryId,
            @RequestParam(name = "shopId", required = false) Long shopId,
            HttpSession session) {
        logger.debug("parentShopCategoryId={}, shopId={}", parentShopCategoryId, shopId);
        Map<String, Object> data = new HashMap<>();
        if(shopId != null) {
            Shop shop = shopService.findShopById(shopId);
            if(shop == null) {
                return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.SHOP_NOT_FOUND, shopId.toString());
            }
            //验证是否属于当前登录用户
            UserInfo owner = (UserInfo) session.getAttribute("user");
            if(!shop.getOwner().getUserId().equals(owner.getUserId())) {
                logger.error("getshopoperationinitdata - 非法操作：用户[{} - {}] 请求店铺[{} - {}]",
                        owner.getUserId(), owner.getName(), shopId, shop.getShopName());
                return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.ILLEGAL_OPERATION);
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
    
    @PostMapping("/registerormodifyshop")
    @ResponseBody
    public Result registerOrModifyShop(
            @RequestParam(name = "shopId", required = false) Long shopId, 
            @RequestParam(name = "shopName") String shopName, 
            @RequestParam(name = "shopCategory") Long shopCategoryId,
            @RequestParam(name = "shopArea") Integer shopAreaId,
            @RequestParam(name = "shopAddr") String shopAddr,
            @RequestParam(name = "shopPhone") String shopPhone,
            @RequestParam(name = "shopDesc", required = false) String shopDesc,
            @RequestParam(name = "verifyCodeActual") String verifyCodeActual,
            @RequestParam(name = "shopImg", required = false) CommonsMultipartFile shopImg,
            HttpSession session) {
        logger.debug("registershop - shopId={}, shopName={}, shopCategory={}, shopArea={}, shopAddr={}, shopPhone={}, shopDesc={}, verifyCodeActual={}, shopImg={}",
                shopId, shopName, shopCategoryId, shopAreaId, shopAddr, shopPhone, shopDesc, verifyCodeActual, shopImg);
        if(!KaptchaUtil.checkVerifyCode(verifyCodeActual, session)) {
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INVALID_VERIFY_CODE);
        }
        Shop shop = new Shop();
        shop.setShopId(shopId);
        shop.setShopName(shopName);
        shop.setShopAddr(shopAddr);
        shop.setPhone(shopPhone);
        shop.setShopDesc(shopDesc);
        
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(shopCategoryId);
        
        Area area = new Area();
        area.setAreaId(shopAreaId);
        
        //获取登录用户
        UserInfo owner = (UserInfo) session.getAttribute("user");
        
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setOwner(owner);
        
        InputStream shopImgIn = null;
        String suffix = null;
        try {
            if(shopImg != null) {
                shopImgIn = shopImg.getInputStream();
                String originalFilename = shopImg.getOriginalFilename();
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String[] acceptImageTypes = (String[]) commonService.getImageUploadProps().get("acceptImageTypes");
                //验证上传的照片格式是否合法
                if(!CommonUtil.isAcceptedMimeType("image", suffix.substring(1), acceptImageTypes)) {
                    return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INVALID_IMAGE_TYPE, suffix);
                }
            }
            if(shopId == null) {
                shopService.registerShop(shop, shopImgIn, suffix);
            } else {
                shopService.modifyShop(shop, shopImgIn, suffix);
            }
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_FAILURE, e);
        } finally {
            try {
                if(null != shopImgIn) {
                    shopImgIn.close();
                }
            } catch(Exception e) {
                logger.error(e.getMessage(), e);
                return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.OPERATION_FAILURE, e);
            }
        }
    }
}
