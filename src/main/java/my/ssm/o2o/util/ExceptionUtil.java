package my.ssm.o2o.util;

/**  
 * <p>异常类工具</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */  
public final class ExceptionUtil {
    private ExceptionUtil() {}
    public static Throwable getRootCause(Exception e) {
        Throwable curr = e.getCause();
        Throwable prev = null;
        while(null != curr) {
            prev = curr;
            curr = curr.getCause();
        }
        return prev;
    }
    public static Class<? extends Throwable> getRootCauseClazz(Exception e) {
        Throwable rootCause = getRootCause(e);
        return rootCause == null ? null : rootCause.getClass();
    }
    public static String getRootCauseMsg(Exception e) {
        Throwable rootCause = getRootCause(e);
        return rootCause == null ? null : rootCause.getMessage();
    }
}
