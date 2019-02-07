package my.ssm.o2o.util;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.code.kaptcha.Constants;

/**  
 * <p>验证码工具类</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */  
public final class KaptchaUtil {
    private KaptchaUtil() {}
    /**  
     * <p>校验验证码</p>  
     * @param verifyCodeActual 用户键入的验证码
     * @param session session里存放着后台生成的验证码
     * @return  验证结果
     */  
    public static boolean checkVerifyCode(String verifyCodeActual, HttpSession session) {
        String verifyCodeExpected = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        return StringUtils.isNotBlank(verifyCodeActual) && 
                verifyCodeActual.equalsIgnoreCase(verifyCodeExpected);
    }
}
