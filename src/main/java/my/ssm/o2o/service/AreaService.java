package my.ssm.o2o.service;

import java.util.List;

import my.ssm.o2o.entity.Area;

/**  
 * <p>区域服务接口</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */  
public interface AreaService {
    String AREA_LIST = "arealist";
    /**  
     * <p>查找全部的区域</p>  
     * @return  区域集合
     */  
    List<Area> findAll();
}
