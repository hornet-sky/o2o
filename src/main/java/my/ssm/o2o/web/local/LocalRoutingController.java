package my.ssm.o2o.web.local;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.ssm.o2o.entity.LocalAuth;

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
    
    @GetMapping("/login")
    public String login(HttpSession session) {
        return "local/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        LocalAuth localAuth = (LocalAuth) session.getAttribute("localAuth");
        session.invalidate();
        if(localAuth != null && localAuth.getAccount() != null) {
            return "redirect:login?account=" + localAuth.getAccount();
        }
        return "redirect:login";
    }
    
    @GetMapping("/changepassword")
    public String changepassword() {
        return "local/changepassword";
    }
}
