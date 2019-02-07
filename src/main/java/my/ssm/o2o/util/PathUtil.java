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
                    String projectName = ((Properties) SpringContextUtils.getBeanById("prop")).getProperty("project.name");
                    logger.debug("os.name={}, user.home={}, file.separator={}, project.name={}", 
                            osName, userHome, separator, projectName); //os.name=Windows 10, user.home=C:\Users\Wang, file.separator=\, project.name=o2o-webapp
                    imageBaseDirPath = userHome + separator + projectName + separator + "image" + separator;
                    logger.debug("imageBaseDirPath={}", imageBaseDirPath); //C:\Users\Wang\o2o-webapp\image\
                }
            }
        }
        
        return imageBaseDirPath;
    }
    
    /**  
     * <p>获取店铺图片所在目录的相对路径</p>  
     * @param shopId 店铺ID
     * @return  相对于基础路径的相对路径
     */  
    public static String getShopImageDirRelativePath(long shopId) {
        String relativePath = "upload/item/shop/" + shopId + "/";
        logger.debug("shopImageDirRelativePath={}", relativePath);
        return relativePath;
    }
}
