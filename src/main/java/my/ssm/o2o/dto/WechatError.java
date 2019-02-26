package my.ssm.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**  
 * <p>微信错误实体</p>
 * <p>Date: 2019年2月26日</p>
 * @author Wanghui    
 */
@Getter
@Setter
public class WechatError {
    /**  
     * <p>错误编码</p>     
     */
    @JsonProperty("errcode")
    private Integer errcode;
    /**  
     * <p>错误消息</p>     
     */
    @JsonProperty("errmsg")
    private String errmsg;
    
    public String toString() {
        return errcode + " - " + errmsg;
    }
}
