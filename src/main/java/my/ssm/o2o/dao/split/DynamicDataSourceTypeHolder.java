package my.ssm.o2o.dao.split;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * <p>主要用于存取数据源类型</p>
 * <p>Date: 2019年2月8日</p>
 * @author Wanghui    
 */  
public class DynamicDataSourceTypeHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceTypeHolder.class);
    /**  
     * <p>用于存放数据源类型（也就是动态选择数据源时所需的key）</p>
     * ThreadLocal 在同一个线程中拥有属于自己独立的存储空间而不受其他线程干扰，因此是线程安全的。
     */
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DST_MASTER = "master";
    public static final String DST_SLAVE = "slave";
    
    public static String getDataSourceType() {
        String dst = contextHolder.get();
        if(StringUtils.isBlank(dst)) {
            dst = DST_MASTER;
        }
        logger.debug("由LazyConnection驱动获取到的数据源类型是：{}", dst);
        return dst;
    }
    
    /**  
     * <p>这一块是与MyBatis解耦的，不一定非要由MyBatis拦截器来选择数据源类型</p>  
     * @param dst  
     */  
    public static void setDataSourceType(String dst) {
        logger.debug("由MyBatis拦截器选择并注入的数据源类型是：{}", dst);
        contextHolder.set(dst);
    }
    
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
