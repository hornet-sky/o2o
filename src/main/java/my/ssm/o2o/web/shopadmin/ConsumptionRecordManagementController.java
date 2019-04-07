package my.ssm.o2o.web.shopadmin;

import java.util.List;
import java.util.Map;

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
import my.ssm.o2o.enums.ConsumptionRecordOperStateEnum;
import my.ssm.o2o.service.ConsumptionRecordService;

/**  
 * <p>消费记录管理控制器</p>
 * <p>Date: 2019年4月6日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class ConsumptionRecordManagementController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ConsumptionRecordService consumptionRecordService;
    
    @GetMapping("/loadconsumptionrecordlist")
    @ResponseBody
    public Result loadConsumptionRecordList(@RequestParam(name = "shopId", required = true) Long shopId, 
            @RequestParam(name = "searchKey", required = false) String searchKey,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo, 
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            return consumptionRecordService.listConsumptionRecordOnShopkeeperSide(shopId, searchKey, pageNo, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<ConsumptionRecord, ConsumptionRecordOperStateEnum>(ConsumptionRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/loadstatisticsinfoforchart")
    @ResponseBody
    public Result loadStatisticsInfoForChart(@RequestParam(name = "shopId", required = true) Long shopId) {
        try {
            List<List<Map<String, Object>>> salesVolumeForThreeDays = consumptionRecordService.listSalesVolumeForThreeDays(shopId);
            return new OperationResult<List<Map<String, Object>>, ConsumptionRecordOperStateEnum>(ConsumptionRecordOperStateEnum.OPERATION_SUCCESS, salesVolumeForThreeDays);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<List<Map<String, Object>>, ConsumptionRecordOperStateEnum>(ConsumptionRecordOperStateEnum.OPERATION_FAILURE, e);
        }
    }
}
