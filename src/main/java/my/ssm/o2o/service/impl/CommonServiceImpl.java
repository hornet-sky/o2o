package my.ssm.o2o.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import my.ssm.o2o.service.CommonService;

/**  
 * <p>通用服务接口实现类</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */ 
@Service
public class CommonServiceImpl implements CommonService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**  
     * <p>单个上传文件的大小，单位B</p>     
     */
    @Value("#{prop['upload.maxUploadSize']}")
    private Long maxUploadSize;
    /**  
     * <p>允许上传的图片类型，例如“image/jpg”</p>     
     */
    @Value("#{prop['upload.acceptImageTypes']}")
    private String[] acceptImageTypes;
    /**  
     * <p>一次提交中上传图片的最大个数</p>     
     */
    @Value("#{prop['upload.maxImageCount']}")
    private Integer maxImageCount;
    /**  
     * <p>上传资源的物理基础路径，例如“C:/Users/Wang/static__resources/”</p>     
     */
    @Value("#{prop['upload.baseDir']}")
    private String uploadBaseDir;
    @Override
    public Map<String, Object> getImageUploadProps() {
        logger.debug("maxUploadSize={}, acceptImageTypes={}", maxUploadSize, Arrays.toString(acceptImageTypes));
        Map<String, Object> props = new HashMap<>();
        props.put("maxUploadSize", maxUploadSize);
        props.put("acceptImageTypes", acceptImageTypes);
        props.put("maxImageCount", maxImageCount);
        return props;
    }
    @Override
    public String getResourcesServerContextPath() {
        logger.debug("uploadBaseDir={}", uploadBaseDir);
        String basePath = uploadBaseDir;
        if(basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        return basePath.substring(basePath.lastIndexOf("/"));
    }

}
