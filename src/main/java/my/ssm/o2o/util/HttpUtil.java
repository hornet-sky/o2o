package my.ssm.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**  
 * <p>Http工具类</p>
 * <p>Date: 2019年3月5日</p>
 * @author Wanghui    
 */  
public final class HttpUtil {
    private HttpUtil() {}
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
