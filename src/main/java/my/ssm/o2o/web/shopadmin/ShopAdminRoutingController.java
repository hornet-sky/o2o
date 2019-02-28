package my.ssm.o2o.web.shopadmin;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.service.ShopService;

/**  
 * <p>店铺管理模块路由控制器</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminRoutingController {
    private Logger logger = LoggerFactory.getLogger(ShopAdminRoutingController.class);
    @Autowired
    private ShopService shopService;
    
    @GetMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }
    
    @GetMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }
    
    @GetMapping("/shopmanagement")
    public String shopManagement(
            @RequestParam("shopId") Long shopId,
            HttpSession session) {
        UserInfo owner = (UserInfo) session.getAttribute("user");
        Shop shop = shopService.findShopById(shopId);
        if(shop == null || !shop.getOwner().getUserId().equals(owner.getUserId())) {
            logger.error("shopmanagement - 非法操作：用户[{} - {}] 请求店铺[{} - {}]", 
                    owner.getUserId(), owner.getName(), shopId, shop == null ? null : shop.getShopId());
            return "redirect:shoplist";
        }
        session.setAttribute("currShop", shop); //存放到session里避免后续操作反复查询数据库
        return "shop/shopmanagement";
    }
    
    @GetMapping("/productcategorymanagement")
    public String productCategoryManagement(@RequestParam("shopId") Long shopId,
            HttpSession session) {
        Shop currShop = (Shop) session.getAttribute("currShop");
        UserInfo owner = (UserInfo) session.getAttribute("user");
        if(currShop == null || !currShop.getShopId().equals(shopId)) {
            logger.error("productcategorymanagement - 非法操作：用户[{} - {}] 请求店铺ID[{}] 缓存店铺ID[{}]", 
                    owner.getUserId(), owner.getName(), shopId, currShop == null ? null : currShop.getShopId());
            return "redirect:shoplist";
        }
        return "shop/productcategorymanagement";
    }
    
    @GetMapping("/productmanagement")
    public String shopList(@RequestParam("shopId") Long shopId,
            HttpSession session) {
        Shop currShop = (Shop) session.getAttribute("currShop");
        UserInfo owner = (UserInfo) session.getAttribute("user");
        if(currShop == null || !currShop.getShopId().equals(shopId)) {
            logger.error("productmanagement - 非法操作：用户[{} - {}] 请求店铺ID[{}] 缓存店铺ID[{}]", 
                    owner.getUserId(), owner.getName(), shopId, currShop == null ? null : currShop.getShopId());
            return "redirect:shoplist";
        }
        return "shop/productmanagement";
    }
    
    @GetMapping("/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }
}
