package my.ssm.o2o.dao;

import java.util.List;

import my.ssm.o2o.entity.HeadLine;

/**  
 * <p>前端滚动条数据访问接口</p>
 * <p>Date: 2019年2月16日</p>
 * @author Wanghui    
 */  
public interface HeadLineDao {
    List<HeadLine> findByCondition(HeadLine condition);
}
