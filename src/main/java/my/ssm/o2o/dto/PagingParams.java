package my.ssm.o2o.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.ssm.o2o.enums.Direction;

/**  
 * <p>分页参数</p>
 * <p>Date: 2019年2月10日</p>
 * @author Wanghui    
 */  
@NoArgsConstructor
@Getter
@Setter
public class PagingParams {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer rowIndex;
    private Integer rowSize;
    private Map<String, Direction> orderRuleMap = new HashMap<>();
    public PagingParams(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    public PagingParams(Integer pageNo, Integer pageSize, String orderField, Direction direction) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        orderRuleMap.put(orderField, direction);
    }
    public Integer getRowIndex() {
        if(rowIndex == null && pageNo != null) {
            if(pageNo < 1) {
                pageNo = 1;
            }
            rowIndex = (pageNo - 1) * pageSize;
        }
        return rowIndex;
    }
    public Integer getRowSize() {
        if(rowSize == null && pageSize != null) {
            if(pageSize < 0) {
                pageSize = 0;
            }
            rowSize = pageSize;
        }
        return rowSize;
    }
    public void addOrderRule(String field, Direction direction) {
        if(StringUtils.isBlank(field))
            return;
        if(direction == null)
            direction = Direction.ASC;
        orderRuleMap.put(field, direction);
    }
    public String getOrderRules() {
        if(orderRuleMap.size() == 0) {
            return null;
        }
        String ruleStr = "";
        for(Entry<String, Direction> entry : orderRuleMap.entrySet()) {
            if(!"".equals(ruleStr)) {
                ruleStr += ", ";
            }
            ruleStr += entry.getKey() + " " + entry.getValue();
        }
        return ruleStr;
    }
    public boolean isOrderRuleMapEmpty() {
        return orderRuleMap.isEmpty();
    }
}
