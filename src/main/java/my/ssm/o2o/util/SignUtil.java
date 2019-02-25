package my.ssm.o2o.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * <p>签名校验工具</p>
 * <p>Date: 2019年2月23日</p>
 * @author Wanghui    
 */  
public final class SignUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
    private static char[] hexDigits = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static char highDigit, lowDigit;
    private SignUtil() {}
    
    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) throws NoSuchAlgorithmException {
        logger.debug("checkSignature [signature={}, timestamp={}, nonce={}, token={}]", 
                signature, timestamp, nonce, token);
        if(StringUtils.isBlank(token)) {
            return false;
        }
        String[] arr = new String[] {token, timestamp, nonce};
        Arrays.sort(arr);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digested = md.digest(StringUtils.join(arr).getBytes());
        String target = byteArr2HexStr(digested);
        logger.debug("target={}", target);
        return target.equals(signature);
    }
    
    private static String byteArr2HexStr(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        for(byte b : byteArr) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }

    private static String byte2HexStr(byte b) {
        highDigit = hexDigits[b >>> 4 & 0x0f];
        lowDigit = hexDigits[b & 0x0f];
        return StringUtils.join(highDigit, lowDigit);
    }
}
