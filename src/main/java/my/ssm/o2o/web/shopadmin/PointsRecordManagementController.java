package my.ssm.o2o.web.shopadmin;

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
import my.ssm.o2o.entity.PointsRecord;
import my.ssm.o2o.enums.PointsRecordOperStateEnum;
import my.ssm.o2o.service.PointsRecordService;

/**  
 * <p>积分记录管理控制器</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class PointsRecordManagementController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PointsRecordService pointsRecordService;
    
    @GetMapping("/loadpointsexpenditurerecordlist")
    @ResponseBody
    public Result loadPointsExpenditureRecordList(@RequestParam(name="shopId", required = true) Long shopId, 
            @RequestParam(name="searchKey", required = false) String searchKey,
            @RequestParam(name="pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name="pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            return pointsRecordService.listPointsRecordOnShopkeeperSide(shopId, searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<PointsRecord, PointsRecordOperStateEnum>(PointsRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }

    @GetMapping("/loadconsumerpointslist")
    @ResponseBody
    public Result loadConsumerPointsList(@RequestParam(name="shopId", required = true) Long shopId, 
            @RequestParam(name="searchKey", required = false) String searchKey,
            @RequestParam(name="pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name="pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            return pointsRecordService.listConsumerTotalPointsRecordOnShopkeeperSide(shopId, searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<PointsRecord, PointsRecordOperStateEnum>(PointsRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
}
