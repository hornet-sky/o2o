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
 * <p>店铺管理控制器</p>
 * <p>Date: 2019年2月6日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController { //TODO 给两个类改名
    private Logger logger = LoggerFactory.getLogger(ShopAdminController.class);
    @Autowired
    private ShopService shopService;
    
    @GetMapping("/shopoperation")
    public String shopRegister() {
        return "shop/shopoperation";
    }
    
    @GetMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }
    
    @GetMapping("/shopmanagement")
    public String shopManagement(
            @RequestParam("shopId") Integer shopId,
            HttpSession session) {
        UserInfo owner = (UserInfo) session.getAttribute("user");
        Shop shop = shopService.findShopById(shopId);
        if(shop == null || !shop.getOwner().getUserId().equals(owner.getUserId())) {
            logger.error("shopmanagement - 非法操作：用户[{} - {}] 请求店铺[{} - {}]", 
                    owner.getUserId(), owner.getName(), shopId, shop == null ? null : shop.getShopName());
            return "redirect:shoplist";
        }
        session.setAttribute("currShop", shop); //存放到session里避免后续操作反复查询数据库
        return "shop/shopmanagement";
    }
}
