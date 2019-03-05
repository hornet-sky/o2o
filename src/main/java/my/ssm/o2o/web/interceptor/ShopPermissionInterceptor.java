package my.ssm.o2o.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.entity.Shop;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.ShopOperStateEnum;
import my.ssm.o2o.util.HttpUtil;
import my.ssm.o2o.util.JacksonUtil;
import my.ssm.o2o.util.URLEncoderUtil;

/**  
 * <p>检查用户是否有操作当前店铺权限的拦截器</p>
 * <p>Date: 2019年3月5日</p>
 * @author Wanghui    
 */  
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Shop currShop = (Shop) session.getAttribute("currShop");
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        String shopId = request.getParameter("shopId");
        String reqUri = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        if(StringUtils.isBlank(shopId)) {
            logger.error("非法操作 [原因：请求参数店铺ID为空] [请求URI：{}] [登录用户：{} - {}]", reqUri, userInfo.getName(), userInfo.getUserId());
            if(HttpUtil.isAjaxRequest(request)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JacksonUtil.toJson(new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.ILLEGAL_OPERATION, "未指明要操作的店铺")));
            } else {
                StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                        .append("/shopadmin/shoplist?msg=")
                        .append(URLEncoderUtil.encode("非法操作：未指明要操作的店铺"));
                response.sendRedirect(redirectUri.toString());
            }
            return false;
        }
        if(currShop == null) {
            logger.error("非法操作 [原因：当前会话未缓存店铺实例] [请求URI：{}] [登录用户：{} - {}]", reqUri, userInfo.getName(), userInfo.getUserId());
            if(HttpUtil.isAjaxRequest(request)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JacksonUtil.toJson(new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.ILLEGAL_OPERATION, "当前会话未缓存店铺实例")));
            } else {
                StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                        .append("/shopadmin/shoplist?msg=")
                        .append(URLEncoderUtil.encode("非法操作：当前会话未缓存店铺实例"));
                response.sendRedirect(redirectUri.toString());
            }
            return false;
        }
        if(!shopId.equals(currShop.getShopId().toString())) {
            logger.error("非法操作 [原因：请求参数店铺ID（{}）与当前会话缓存的店铺ID（{}）不一致] [请求URI：{}] [登录用户：{} - {}]", shopId, currShop.getShopId(), reqUri, userInfo.getName(), userInfo.getUserId());
            if(HttpUtil.isAjaxRequest(request)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JacksonUtil.toJson(new OperationResult<Shop, ShopOperStateEnum>(ShopOperStateEnum.ILLEGAL_OPERATION, "要操作的店铺与当前会话缓存的店铺不一致")));
            } else {
                StringBuilder redirectUri = new StringBuilder(request.getContextPath())
                        .append("/shopadmin/shoplist?msg=")
                        .append(URLEncoderUtil.encode("非法操作：要操作的店铺与当前会话缓存的店铺不一致"));
                response.sendRedirect(redirectUri.toString());
            }
            return false;
        }
        return true;
    }

}
