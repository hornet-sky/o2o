package my.ssm.o2o.web.frontend;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.ConsumptionRecord;
import my.ssm.o2o.entity.PointsRecord;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.ConsumptionRecordOperStateEnum;
import my.ssm.o2o.enums.PointsRecordOperStateEnum;
import my.ssm.o2o.service.ConsumptionRecordService;
import my.ssm.o2o.service.PointsRecordService;

/**  
 * <p>顾客信息控制器</p>
 * <p>Date: 2019年4月7日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class ConsumerInfoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ConsumptionRecordService consumptionRecordService;
    @Autowired
    private PointsRecordService pointsRecordService;
    
    @GetMapping("/loadconsumptionrecordlist")
    @ResponseBody
    public Result loadConsumptionRecordList(@RequestParam(name = "searchKey", required = false) String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        try {
            return consumptionRecordService.listConsumptionRecordOnConsumerSide(userInfo.getUserId(), searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<ConsumptionRecord, ConsumptionRecordOperStateEnum>(ConsumptionRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/loadpointsrecordlist")
    @ResponseBody
    public Result loadPointsRecordList(@RequestParam(name="searchKey", required = false) String searchKey,
            @RequestParam(name="pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name="pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        try {
            return pointsRecordService.listShopTotalPointsRecordOnConsumerSide(userInfo.getUserId(), searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<PointsRecord, PointsRecordOperStateEnum>(PointsRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
}
