package my.ssm.o2o.web.wechat;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.ssm.o2o.util.SignUtil;

/**  
 * <p>与微信进行交互的控制器</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/wechat")
public class WechatController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**  
     * <p>验证授权</p>  
     * @param signature 微信发来的加密签名。融合了开发者在公众平台里设置的token和当前时间戳、随机数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr  随机字符串，用于回显
     */  
    @GetMapping("/checkAuthorization")
    public void checkAuthorization(String signature, String timestamp, String nonce, String echostr, 
            HttpServletResponse response) {
        try {
            if(SignUtil.checkSignature(signature, timestamp, nonce)) { //如果校验成功，则回显echostr
                PrintWriter writer = response.getWriter();
                writer.print(echostr);
            }
        } catch (Exception e) {
            logger.error("验证授权时发生异常", e);
        }
    }
}
