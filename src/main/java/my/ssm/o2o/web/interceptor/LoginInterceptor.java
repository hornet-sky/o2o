package my.ssm.o2o.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.util.URLEncoderUtil;

/**  
 * <p>检查用户是否登录的拦截器，如果用户未登录则重定向到登录页面</p>
 * <p>Date: 2019年3月5日</p>
 * @author Wanghui    
 */  
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if(userInfo == null) { //未登录
            StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                    .append("/local/login?msg=")
                    .append(URLEncoderUtil.encode("请先登录"))
                    .append("&targetUri=");
            String targetUri = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            redirectUri.append(URLEncoderUtil.encode(targetUri));
            response.sendRedirect(redirectUri.toString());
            return false;
        }
        //已登录
        Integer enableStatus = userInfo.getEnableStatus();
        if(enableStatus == 0) {
            StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                    .append("/local/login?msg=")
                    .append(URLEncoderUtil.encode("该账号已被禁用"));
            response.sendRedirect(redirectUri.toString());
            return false;
        }
        if(enableStatus == 2) {
            StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                    .append("/local/login?msg=")
                    .append(URLEncoderUtil.encode("该账号正在审核中"));
            response.sendRedirect(redirectUri.toString());
            return false;
        }
        return true;
    }

}
