package my.ssm.o2o.dto;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    private String fileName;
    private String suffix;
    public ImageHolder(InputStream inputStream, String fileName) {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.suffix = fileName.substring(fileName.lastIndexOf("."));
    }
    public ImageHolder(CommonsMultipartFile image) throws IOException {
        this.inputStream = image.getInputStream();
        this.fileName = image.getOriginalFilename();
        this.suffix = this.fileName.substring(this.fileName.lastIndexOf("."));
    }
    public void close() throws IOException {
        if(inputStream != null) {
            inputStream.close();
        }
    }
}
