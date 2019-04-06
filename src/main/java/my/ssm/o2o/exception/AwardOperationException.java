package my.ssm.o2o.exception;

/**  
 * <p>奖品业务操作异常</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
public class AwardOperationException extends RuntimeException {
    private static final long serialVersionUID = -6677225371022357032L;

    public AwardOperationException() {
        super();
    }

    public AwardOperationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AwardOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwardOperationException(String message) {
        super(message);
    }

    public AwardOperationException(Throwable cause) {
        super(cause);
    }
}
