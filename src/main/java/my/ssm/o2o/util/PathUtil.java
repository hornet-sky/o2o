package my.ssm.o2o.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * <p>路径工具类</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public final class PathUtil {
    private static String imageBaseDirPath;
    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);
    private PathUtil() {}
    /**  
     * <p>获取保存图片的基础路径</p>  
     * @return  基础路径
     */  
    public static String getImageBaseDirPath() {
        if(imageBaseDirPath == null) {
            synchronized(PathUtil.class) {
                if(imageBaseDirPath == null) {
                    String osName = System.getProperty("os.name");
                    String userHome = System.getProperty("user.home");
                    String separator = System.getProperty("file.separator");
                    Properties props = (Properties) SpringContextUtils.getBeanById("prop");
                    String projectName = props.getProperty("project.name");
                    imageBaseDirPath = props.getProperty("upload.baseDir");
                    logger.debug("os.name={}, user.home={}, file.separator={}, project.name={}, upload.baseDir={}", 
                            osName, userHome, separator, projectName, imageBaseDirPath); //os.name=Windows 10, user.home=C:\Users\Wang, file.separator=\, project.name=o2o_webapp, C:\Users\Wang\static__resources\
                }
            }
        }
        return imageBaseDirPath;
    }
    
    /**  
     * <p>获取保存店铺相关图片的相对路径</p>  
     * @param shopId 店铺ID
     * @return  相对于基础路径的相对路径
     */  
    public static String getShopImageRelativeDirPath(long shopId) {
        Properties props = (Properties) SpringContextUtils.getBeanById("prop");
        String shopImageRelativeDir = props.getProperty("upload.relativeDir") + "shop/" + shopId + "/";
        logger.debug("shopImageRelativeDirPath={}", shopImageRelativeDir);
        return shopImageRelativeDir;
    }
}
