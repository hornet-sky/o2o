package my.ssm.o2o.exception;

/**  
 * <p>店铺业务操作异常</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public class ShopOperationException extends RuntimeException {
    private static final long serialVersionUID = -2112827897350840503L;

    public ShopOperationException() {
        super();
    }

    public ShopOperationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShopOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopOperationException(String message) {
        super(message);
    }

    public ShopOperationException(Throwable cause) {
        super(cause);
    }
}
