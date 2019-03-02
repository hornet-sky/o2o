package my.ssm.o2o.service;

import java.util.List;

import my.ssm.o2o.entity.HeadLine;

/**  
 * <p>前端滚动条服务接口</p>
 * <p>Date: 2019年2月16日</p>
 * @author Wanghui    
 */  
public interface HeadLineService {
    String HEAD_LINE_LIST = "headlinelist";
    /**  
     * <p>根据条件查询前端滚动条列表</p>  
     * @param condition 条件
     * @return  前端滚动条列表
     */  
    List<HeadLine> findByCondition(HeadLine condition);
}
