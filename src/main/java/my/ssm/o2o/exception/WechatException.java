package my.ssm.o2o.exception;

/**  
 * <p>与微信交互产生的异常</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public class WechatException extends RuntimeException {
    private static final long serialVersionUID = -8301083262773554568L;

    public WechatException() {
        super();
    }

    public WechatException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }
}
