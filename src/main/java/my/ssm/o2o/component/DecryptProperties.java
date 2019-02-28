package my.ssm.o2o.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.ClassPathResource;

import my.ssm.o2o.util.DESUtil;

/**  
 * <p>带解码功能的Properties</p>
 * <p>Date: 2019年2月28日</p>
 * @author Wanghui    
 */  
public class DecryptProperties extends Properties {
    private static final long serialVersionUID = 4801614153345008936L;
    /**  
     * <p>待解码的属性名</p>     
     */
    private String[] decryptPropertyNames = {"wechat.token", "wechat.appid", "wechat.secret"};
    public DecryptProperties() {}
    public DecryptProperties(String location) throws IOException {
        if(location.contains("classpath:")) {
            loadClassPathResource(location);
        } else {
            loadResource(location);
        }
    }
    public DecryptProperties(String[] locations) throws IOException {
        for(String location : locations) {
            if(location.contains("classpath:")) {
                loadClassPathResource(location);
            } else {
                loadResource(location);
            }
        }
    }
    private void loadResource(String location) throws IOException {
        try(InputStream in = new FileInputStream(new File(location))) {
            this.load(in);
        }
    }
    private void loadClassPathResource(String location) throws IOException {
        location = location.substring(location.indexOf("classpath:") + 10).trim();
        try(InputStream in = new ClassPathResource(location).getInputStream()) {
            this.load(in);
        }
    }
    @Override
    public synchronized Object get(Object key) {
        Object value =  super.get(key);
        if(ArrayUtils.contains(decryptPropertyNames, key) && value != null) {
            return DESUtil.decrypt(value.toString());
        }
        return value;
    }
}
