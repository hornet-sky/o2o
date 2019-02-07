package my.ssm.o2o.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import my.ssm.o2o.enums.OperStateEnum;

/**  
 * <p>操作结果</p>
 * <p>Date: 2019年2月5日</p>
 * @author Wanghui    
 */ 
@Getter
@Setter
public class OperationResult<T,E extends OperStateEnum> extends Result {
    private T entity;
    private List<T> entityList;
    
    public OperationResult(Integer state, String msg) {
        super(state, msg);
    }
    public OperationResult(E operStateEnum) {
        super(operStateEnum.getState(), operStateEnum.getMsg());
    }
    public OperationResult(E operStateEnum, Throwable t) {
        super(operStateEnum.getState(), operStateEnum.getMsg() + ": " + t.toString());
    }
    public OperationResult(E operStateEnum, String msg) {
        super(operStateEnum.getState(), operStateEnum.getMsg() + ": " + msg);
    }
    public OperationResult(E operStateEnum, T entity) {
        super(operStateEnum.getState(), operStateEnum.getMsg());
        this.entity = entity;
    }
    public OperationResult(E operStateEnum, List<T> entityList) {
        super(operStateEnum.getState(), operStateEnum.getMsg());
        this.entityList = entityList;
    }
}
