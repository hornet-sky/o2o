package my.ssm.o2o.web.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * <p>前端路由控制器</p>
 * <p>Date: 2019年2月16日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class FrontendRoutingController {
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @GetMapping({"/", "/index"})
    public String index() {
        return "frontend/index";
    }
    
    @GetMapping({"/shoplist"})
    public String shopList() {
        return "frontend/shoplist";
    }
    
    @GetMapping({"/productlist"})
    public String productList() {
        return "frontend/productlist";
    }
    
    @GetMapping({"/productdetail"})
    public String productDetail() {
        return "frontend/productdetail";
    }
    
    @GetMapping({"/awardlist"})
    public String awardList() {
        return "frontend/awardlist";
    }
    
    @GetMapping({"/consumptionrecordlist"})
    public String consumptionRecordList() {
        return "frontend/consumptionrecordlist";
    }
    
    @GetMapping({"/pointsrecordlist"})
    public String pointsRecordList() {
        return "frontend/pointsrecordlist";
    }
    
    @GetMapping({"/pointsexpenditurerecordlist"})
    public String pointsExpenditureRecordList() {
        return "frontend/pointsexpenditurerecordlist";
    }
    
    @GetMapping({"/pointsdetaillist"})
    public String pointsDetailList() {
        return "frontend/pointsdetaillist";
    }
}
