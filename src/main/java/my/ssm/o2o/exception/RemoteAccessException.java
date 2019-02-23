package my.ssm.o2o.exception;

/**  
 * <p>远程访问异常</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public class RemoteAccessException extends RuntimeException {
    private static final long serialVersionUID = -8301083262773554567L;

    public RemoteAccessException() {
        super();
    }

    public RemoteAccessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RemoteAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteAccessException(String message) {
        super(message);
    }

    public RemoteAccessException(Throwable cause) {
        super(cause);
    }
}
