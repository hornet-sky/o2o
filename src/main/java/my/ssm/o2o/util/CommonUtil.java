package my.ssm.o2o.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**  
 * <p>包含一些通用的工具方法</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */  
public final class CommonUtil {
    private CommonUtil() {}
    /**  
     * <p>判断是否是可以接受的MIME类型</p>  
     * @param prefix 前缀，例如application
     * @param suffix 后缀，例如json，前缀加后缀即可组合成一个MIME类型“application/json”
     * @param allMimeTypes 全部待比较的MIME类型
     * @return 比较结果。如果为true，则表示“prefix/suffix”包含在allMimeTypes中。
     */  
    public static boolean isAcceptedMimeType(String prefix, String suffix, String[] allMimeTypes) {
        if(allMimeTypes == null || allMimeTypes.length == 0  ||
                StringUtils.isBlank(prefix) || StringUtils.isBlank(suffix)) {
            return false;
        }
        String currMimeType = prefix + "/" + suffix;
        return ArrayUtils.contains(allMimeTypes, currMimeType);
    }
    
    /**  
     * <p>将byte大小转换成更易读的方式</p>  
     * @param size 原始的byte大小，例如1048576
     * @return  可读性更高的结果，例如1.21KB
     */  
    public static String humanizeByteSize(long size) {
        if(size < 0) {
            return null;
        } else if(size < 1024) {
            return size + "B";
        } else if(size < 1024 * 1024) {
            return BigDecimal.valueOf(size)
                .divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_DOWN)
                .toString() + "KB";
        } else if(size < 1024 * 1024 * 1024) {
            return BigDecimal.valueOf(size)
                .divide(BigDecimal.valueOf(1024 * 1024), 2, BigDecimal.ROUND_DOWN)
                .toString() + "MB";
        } else if(size < 1024 * 1024 * 1024 * 1024) {
            return BigDecimal.valueOf(size)
                .divide(BigDecimal.valueOf(1024 * 1024 * 1024), 2, BigDecimal.ROUND_DOWN)
                .toString() + "GB";
        }
        return String.valueOf(size) + "B";
    }
}
