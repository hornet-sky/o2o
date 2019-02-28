package my.ssm.o2o.exception;

/**  
 * <p>使用DES工具类加密解密过程中产生的异常</p>
 * <p>Date: 2019年2月27日</p>
 * @author Wanghui    
 */  
public class DESException extends RuntimeException {
    private static final long serialVersionUID = -8301083262773554569L;

    public DESException() {
        super();
    }

    public DESException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DESException(String message, Throwable cause) {
        super(message, cause);
    }

    public DESException(String message) {
        super(message);
    }

    public DESException(Throwable cause) {
        super(cause);
    }
}
