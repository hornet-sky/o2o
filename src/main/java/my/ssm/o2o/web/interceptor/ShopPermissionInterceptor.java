package my.ssm.o2o.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import my.ssm.o2o.entity.UserInfo;

/**  
 * <p>检查用户是否有操作当前店铺权限的拦截器</p>
 * <p>Date: 2019年3月5日</p>
 * @author Wanghui    
 */  
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        
        
        return true;
    }

}
