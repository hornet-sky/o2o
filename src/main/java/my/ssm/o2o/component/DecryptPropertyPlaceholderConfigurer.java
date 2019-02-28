package my.ssm.o2o.component;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import my.ssm.o2o.util.DESUtil;

/**  
 * <p>带解码功能的占位符配置器</p>
 * <p>Date: 2019年2月27日</p>
 * @author Wanghui    
 */  
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    /**  
     * <p>待解码的属性名</p>     
     */
    private String[] decryptPropertyNames = {"jdbc.username", "jdbc.password"};
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(ArrayUtils.contains(decryptPropertyNames, propertyName) && StringUtils.isNotBlank(propertyValue)) {
            return DESUtil.decrypt(propertyValue);
        }
        return propertyValue;
    }
}
