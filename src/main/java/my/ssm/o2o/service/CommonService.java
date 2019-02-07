package my.ssm.o2o.service;

import java.util.Map;

/**  
 * <p>通用服务接口</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */  
public interface CommonService {
    /**  
     * <p>获取图片上传的参数</p>  
     * @return  参数
     */  
    Map<String, Object> getImageUploadProps();
}
