package my.ssm.o2o.exception;

/**  
 * <p>商品业务操作异常</p>
 * <p>Date: 2019年2月13日</p>
 * @author Wanghui    
 */  
public class ProductOperationException extends RuntimeException {
    private static final long serialVersionUID = -6677225371022357032L;

    public ProductOperationException() {
        super();
    }

    public ProductOperationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProductOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductOperationException(String message) {
        super(message);
    }

    public ProductOperationException(Throwable cause) {
        super(cause);
    }
}
