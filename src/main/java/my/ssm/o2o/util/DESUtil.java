package my.ssm.o2o.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.ssm.o2o.exception.DESException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**  
 * <p>DES加密工具类</p>
 * <p>Date: 2019年2月27日</p>
 * @author Wanghui    
 */  
public final class DESUtil {
    private static final Logger logger = LoggerFactory.getLogger(DESUtil.class);
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "DES";
    private static final String SEED = "tiger";
    private static final Key key;
    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(SEED.getBytes(CHARSET));
            generator.init(random);
            key = generator.generateKey();
        } catch (Exception e) {
            logger.error("构建key过程中产生了异常", e);
            throw new DESException("构建key过程中产生了异常", e);
        }
    }
    private DESUtil() {}
    
    /**  
     * <p>加密</p>  
     * @param clearText 明文
     * @return 密文  
     */  
    public static String encrypt(String clearText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(clearText.getBytes(CHARSET));
            return new BASE64Encoder().encode(doFinal);
        } catch (Exception e) {
            logger.error("加密过程中产生了异常", e);
            throw new DESException("加密过程中产生了异常", e);
        }
    }
    
    /**  
     * <p>解密</p>  
     * @param cipherText 密文
     * @return  明文
     */  
    public static String decrypt(String cipherText) {
        try {
            byte[] data = new BASE64Decoder().decodeBuffer(cipherText);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(data);
            return new String(doFinal, CHARSET);
        } catch (Exception e) {
            logger.error("解密过程中产生了异常", e);
            throw new DESException("解密过程中产生了异常", e);
        }
    }
}
