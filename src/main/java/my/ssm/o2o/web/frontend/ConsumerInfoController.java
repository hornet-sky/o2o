package my.ssm.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

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
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.enums.ConsumptionRecordOperStateEnum;
import my.ssm.o2o.enums.PointsRecordOperStateEnum;
import my.ssm.o2o.service.CommonService;
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
    @Autowired
    private CommonService commonService;
    
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
    public Result loadPointsRecordList(@RequestParam(name = "searchKey", required = false) String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        try {
            return pointsRecordService.listShopTotalPointsRecordOnConsumerSide(userInfo.getUserId(), searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<PointsRecord, PointsRecordOperStateEnum>(PointsRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/loadpointsdetailinitdata")
    @ResponseBody
    public Result loadPointsDetailInitData() {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
    
    @GetMapping("/loadpointsdetaillist")
    @ResponseBody
    public Result loadPointsDetailList(
            @RequestParam(name = "shopId", required = true) Long shopId,
            @RequestParam(name = "beginDate", required = false) String beginDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "pointsShowType", required = false, defaultValue = "0") Integer pointsShowType,
            @RequestParam(name = "searchKey", required = false) String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        try {
            Boolean isPointsInShow = pointsShowType == 0 || pointsShowType == 1;
            Boolean isPointsOutShow = pointsShowType == 0 || pointsShowType == -1;
            return pointsRecordService.listShopPointsRecordDetailOnConsumerSide(userInfo.getUserId(), shopId, beginDate, endDate, searchKey, isPointsInShow, isPointsOutShow, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<PointsRecord, PointsRecordOperStateEnum>(PointsRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
}
