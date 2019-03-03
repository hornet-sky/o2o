package my.ssm.o2o.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.ssm.o2o.exception.MD5Exception;

/**  
 * <p>MD5加密工具类</p>
 * <p>Date: 2019年3月3日</p>
 * @author Wanghui    
 */  
public final class MD5Util {
    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);
    private static final char[] mappingCharsTemplate = {'a', 'l', 'g', 'o', 'r', 'i', 't', 'h', 'm', '5', '6', '7', '8', '9', '0', 'w'};
    private MD5Util() {}
    
    /**  
     * <p>加密</p>  
     * @param clearText 明文
     * @return 密文  
     */  
    public static String encrypt(String clearText) {
        try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(clearText.getBytes());
           byte[] bytes = md.digest();
           char[] mappingChars = new char[bytes.length * 2];
           int i = 0;
           for(byte b : bytes) {
               mappingChars[i++] = mappingCharsTemplate[b >>> 4 & 0x0f]; //高四位
               mappingChars[i++] = mappingCharsTemplate[b & 0x0f]; //低四位
           }
           return new String(mappingChars);
        } catch (Exception e) {
            logger.error("加密过程中产生了异常", e);
            throw new MD5Exception("加密过程中产生了异常", e);
        }
    }
}
