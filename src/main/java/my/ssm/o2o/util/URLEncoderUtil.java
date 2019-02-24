package my.ssm.o2o.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * <p>URL编码工具类</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public final class URLEncoderUtil {
    private static final Logger logger = LoggerFactory.getLogger(URLEncoderUtil.class);
    private static String defaultCharset = "UTF-8";
    private URLEncoderUtil() {}
    public static String encode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("编码期间产生异常", e);
            return content;
        }
    }
    public static String encode(String content) {
        return encode(content, defaultCharset);
    }
}
