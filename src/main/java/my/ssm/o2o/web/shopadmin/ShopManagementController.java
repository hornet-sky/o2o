package my.ssm.o2o.web.shopadmin;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.ShopCategory;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ShopService;
import my.ssm.o2o.util.CommonUtil;
import my.ssm.o2o.util.KaptchaUtil;

/**  
 * <p>店铺管理控制器</p>
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
    private CommonService commonService;
    
    @PostMapping("/registershop")
    @ResponseBody
    public Result registerShop(
            @RequestParam(name = "shopName") String shopName, 
            @RequestParam(name = "shopCategory") Long shopCategoryId,
            @RequestParam(name = "shopArea") Integer shopAreaId,
            @RequestParam(name = "shopAddr") String shopAddr,
            @RequestParam(name = "shopPhone") String shopPhone,
            @RequestParam(name = "shopDesc", required = false) String shopDesc,
            @RequestParam(name = "verifyCodeActual") String verifyCodeActual,
            @RequestParam(name = "shopImg", required = false) CommonsMultipartFile shopImg,
            HttpSession session) {
        logger.debug("registershop - shopName={}, shopCategory={}, shopArea={}, shopAddr={}, shopPhone={}, shopDesc={}, verifyCodeActual={}, shopImg={}",
                shopName, shopCategoryId, shopAreaId, shopAddr, shopPhone, shopDesc, verifyCodeActual, shopImg);
        if(!KaptchaUtil.checkVerifyCode(verifyCodeActual, session)) {
            return new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.INVALID_VERIFY_CODE);
        }
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddr(shopAddr);
        shop.setPhone(shopPhone);
        shop.setShopDesc(shopDesc);
        
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(shopCategoryId);
        
        Area area = new Area();
        area.setAreaId(shopAreaId);
        
        //TODO owner应该从session中获取，是登录用户
        UserInfo owner = new UserInfo();
        owner.setUserId(1L);
        
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
            shopService.register(shop, shopImgIn, suffix);
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
