package my.ssm.o2o.web.superadmin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Area;
import my.ssm.o2o.enums.AreaOperStateEnum;
import my.ssm.o2o.service.AreaService;

/**  
 * <p>区域控制器</p>
 * <p>Date: 2019年2月4日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/superadmin")
public class AreaController {
    private Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;
    
    @GetMapping("/listarea")
    @ResponseBody
    public Result listArea() {
        try {
            //TODO 如果修改成分页操作（注意处理缓存CacheDao），或者直接就不分页了
            List<Area> areaList = areaService.findAll();
            return new PagingResult<Area>(areaList, new Long(areaList.size()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Area, AreaOperStateEnum>(AreaOperStateEnum.OPERATION_FAILURE, e.getMessage());
        }
    }
}
