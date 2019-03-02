package my.ssm.o2o.exception;
  
/**  
 * <p>区域业务操作异常</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
public class AreaException extends RuntimeException {
    private static final long serialVersionUID = -8301083262773554519L;

    public AreaException() {
        super();
    }

    public AreaException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AreaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AreaException(String message) {
        super(message);
    }

    public AreaException(Throwable cause) {
        super(cause);
    }
}
