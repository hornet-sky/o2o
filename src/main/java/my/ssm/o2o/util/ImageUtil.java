package my.ssm.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.ssm.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**  
 * <p>图片工具类</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */  
public final class ImageUtil {
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static Random random = new Random();
    private ImageUtil() {}
    
    /**  
     * <p>生成封面图</p>  
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     * @param destDirRelativePath 封面图所在目录的相对路径
     * @return  封面图的相对路径
     * @throws IOException 转换过程中的IO异常
     */  
    public static String generateCoverImage(ImageHolder image, String destDirRelativePath) throws IOException {
        return generateThumbnail(image, destDirRelativePath, 200, 200, 0.8);
    }
    
    /**  
     * <p>生成详情图</p>  
     * @param image  图片文件持有器。其内部包含了图片文件的输入流和文件名等信息。
     * @param destDirRelativePath 详情图所在目录的相对路径
     * @return  详情图的相对路径
     * @throws IOException 转换过程中的IO异常
     */  
    public static String generateDetailImage(ImageHolder image, String destDirRelativePath) throws IOException {
        return generateThumbnail(image, destDirRelativePath, 640, 337, 0.9);
    }
    
    /**  
     * <p>保存成缩略图</p>  
     * @param image 待保存的图片
     * @param destDirRelativePath 缩略图所在目录的相对路径
     * @param width 缩略图的宽度
     * @param height 缩略图的高度
     * @param quality 缩略图的质量
     * @return 缩略图的相对路径
     * @throws IOException  保存过程中出现异常
     */  
    public static String generateThumbnail(ImageHolder image, String destDirRelativePath, int width, int height, double quality) throws IOException {
        logger.debug("destDirRelativePath={}", destDirRelativePath);
        String destFileRelativePath = destDirRelativePath + generateRandomFileName() + image.getSuffix();
        logger.debug("destFileRelativePath={}", destFileRelativePath);
        String destFileAbsolutePath = PathUtil.getImageBaseDirPath() + destFileRelativePath;
        logger.debug("destFileAbsolutePath={}", destFileAbsolutePath);
        File destFile = new File(destFileAbsolutePath);
        File destDir = destFile.getParentFile();
        if(!destDir.exists()) {
            destDir.mkdirs();
        }
        InputStream wmIn = null;
        try {
            wmIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("image/watermark.png");
            Thumbnails.of(image.getInputStream()).size(width, height)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(wmIn), 0.5f)
                    .outputQuality(quality)
                    .toFile(destFile);
        } finally {
            if(wmIn != null)
                wmIn.close();
        }
        return destFileRelativePath;
    }
    
    /**  
     * <p>删除指定路径的文件或目录</p>  
     * @param destRelativePath  文件或目录的相对路径
     * @return 成功删除则返回true
     */  
    public static void remove(String destRelativePath) {
        logger.debug("准备删除的文件或目录的相对路径[{}]", destRelativePath);
        if(StringUtils.isBlank(destRelativePath)) {
            return;
        }
        String destAbsolutePath = PathUtil.getImageBaseDirPath() + destRelativePath;
        logger.debug("准备删除的文件或目录的绝对路径[{}]", destAbsolutePath);
        File file = new File(destAbsolutePath);
        if(!file.exists()) {
            logger.debug("目标文件或目录不存在[{}]", destAbsolutePath);
            return;
        }
        deepDelete(file);
    }
    
    private static void deepDelete(File file) {
        if(file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for(File sf : subFiles) {
                deepDelete(sf);
            }
        }
        file.delete();
    }
    
    /**  
     * <p>获得随机的文件名</p>  
     * @return  随机文件名，格式“yyyyMMddHHmmssSSS+随机4位整数”
     */  
    public static String generateRandomFileName() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(fmt) + (1000 + random.nextInt(9000));
    }
    
    /**  
     * <p>获取上传文件的后缀名</p>  
     * @param multipartFile 上传的文件
     * @return  后缀名，例如“.jpg”
     */  
    public static String getSuffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
