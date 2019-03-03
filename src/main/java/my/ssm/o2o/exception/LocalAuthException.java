package my.ssm.o2o.exception;

/**  
 * <p>本地授权异常</p>
 * <p>Date: 2019年3月3日</p>
 * @author Wanghui    
 */  
public class LocalAuthException extends RuntimeException {
    private static final long serialVersionUID = -8301083262773554559L;

    public LocalAuthException() {
        super();
    }

    public LocalAuthException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LocalAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalAuthException(String message) {
        super(message);
    }

    public LocalAuthException(Throwable cause) {
        super(cause);
    }
}
