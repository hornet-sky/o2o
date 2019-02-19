package my.ssm.o2o.dto;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.ssm.o2o.enums.PhoneOrientationEnum;
/**  
 * <p>图片持有器</p>
 * <p>Date: 2019年2月12日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageHolder {
    private InputStream inputStream;
    private BufferedImage bufferedImage;
    private String fileName;
    private String suffix;
    private int width;
    private int height;
    private PhoneOrientationEnum orientation;
    public ImageHolder(InputStream inputStream, String fileName) throws IOException {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.suffix = fileName.substring(fileName.lastIndexOf("."));
        this.bufferedImage = ImageIO.read(inputStream);
        if(this.bufferedImage != null) {
            this.width = this.bufferedImage.getWidth();
            this.height = this.bufferedImage.getHeight();
            this.orientation = this.width >= this.height ? PhoneOrientationEnum.HORIZONTAL : PhoneOrientationEnum.VERTICAL;
        }
    }
    public ImageHolder(CommonsMultipartFile image) throws IOException {
        this.inputStream = image.getInputStream();
        this.fileName = image.getOriginalFilename();
        this.suffix = this.fileName.substring(this.fileName.lastIndexOf("."));
        this.bufferedImage = ImageIO.read(inputStream);
        if(this.bufferedImage != null) {
            this.width = this.bufferedImage.getWidth();
            this.height = this.bufferedImage.getHeight();
            this.orientation = this.width >= this.height ? PhoneOrientationEnum.HORIZONTAL : PhoneOrientationEnum.VERTICAL;
        }
    }
    public void close() throws IOException {
        if(inputStream != null) {
            inputStream.close();
        }
    }
}
