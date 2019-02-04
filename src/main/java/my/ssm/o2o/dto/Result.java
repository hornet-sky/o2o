package my.ssm.o2o.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  
 * <p>控制器返回的结果集</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */ 
//将结果中的属性名由驼峰转成下划线
//@JsonNaming(SnakeCaseStrategy.class)
//将结果中属性值为null的属性排除掉
//@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Result {
    private Boolean success = true;
    private String errMsg;
    public Result(String errMsg) {
        this.success = false;
        this.errMsg = errMsg;
    }
}
