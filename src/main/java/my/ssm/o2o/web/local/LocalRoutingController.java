package my.ssm.o2o.web.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**  
 * <p>本地授权模块路由控制器</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/local")
public class LocalRoutingController {
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(LocalRoutingController.class);
    
    @GetMapping("/auth")
    public String auth() {
        return "local/auth";
    }
}
