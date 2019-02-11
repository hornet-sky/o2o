package my.ssm.o2o.dto;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  
 * <p>前端UI中Grid组件所需的结果集</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PagingResult<T> extends Result {
    private List<T> rows = Collections.emptyList();
    private Long total = 0L;
}
