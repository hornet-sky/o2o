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
    @Value("#{prop['upload.maxUploadSize']}")
    private Long maxUploadSize;
    @Value("#{prop['upload.acceptImageTypes']}")
    private String[] acceptImageTypes;
    @Override
    public Map<String, Object> getImageUploadProps() {
        logger.debug("maxUploadSize={}, acceptImageTypes={}", maxUploadSize, Arrays.toString(acceptImageTypes));
        Map<String, Object> props = new HashMap<>();
        props.put("maxUploadSize", maxUploadSize);
        props.put("acceptImageTypes", acceptImageTypes);
        return props;
    }

}
