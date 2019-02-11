package my.ssm.o2o.dto;

import lombok.Getter;
import lombok.Setter;

/**  
 * <p>分页参数</p>
 * <p>Date: 2019年2月10日</p>
 * @author Wanghui    
 */  
@Getter
@Setter
public class PagingParams {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer rowIndex;
    private Integer rowSize;
    public Integer getRowIndex() {
        if(rowIndex == null) {
            if(pageNo < 1) {
                pageNo = 1;
            }
            rowIndex = (pageNo - 1) * pageSize;
        }
        return rowIndex;
    }
    public Integer getRowSize() {
        if(rowSize == null) {
            if(pageSize < 0) {
                pageSize = 0;
            }
            rowSize = pageSize;
        }
        return rowSize;
    }
}
