package my.ssm.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.Award;
import my.ssm.o2o.enums.AwardOperStateEnum;
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.service.AwardService;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.util.KaptchaUtil;

/**  
 * <p>奖品管理功能控制器</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/shopadmin")
public class AwardManagementController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AwardService awardService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/getawardlist")
    @ResponseBody
    public Result getAwardList(Long shopId, PagingParams pagingParams, HttpSession session) {
        Award condition = new Award();
        condition.setShopId(shopId);
        try {
            return awardService.listAward(condition, null, pagingParams);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_FAILURE, e);
        }
    }
    
    @GetMapping("/shelveaward")
    @ResponseBody
    public Result shelveAward(@RequestParam("awardId") Long awardId, 
            @RequestParam("enableStatus") Integer enableStatus) {
        try {
            int effectedRows = awardService.shelveAward(awardId, enableStatus);
            if(effectedRows > 0) {
                return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_SUCCESS);
            }
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_FAILURE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_FAILURE.getState(), e.getMessage());
        }
    }
    
    @GetMapping("/getawardoperationinitdata")
    @ResponseBody
    public Result getAwardOperationInitData(
            @RequestParam(name = "awardId", required = false) Long awardId,
            @RequestParam(name = "shopId") Long shopId,
            HttpSession session) {
        logger.debug("awardId={}, shopId={}", awardId, shopId);
        Map<String, Object> data = new HashMap<>();
        if(awardId != null) {
            Award award = awardService.findAwardById(awardId);
            if(award == null) {
                return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.AWARD_NOT_FOUND, awardId.toString());
            }
            data.put("award", award);
        }
        try {
            data.put("imageUploadProps", commonService.getImageUploadProps());
            return new OperationResult<Map<String, Object>, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_SUCCESS, data);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Map<String, Object>, AwardOperStateEnum>(AwardOperStateEnum.INITIALIZATION_FAILURE, e);
        }
    }
    
    @PostMapping("/addormodifyaward")
    @ResponseBody
    public Result addOrModifyAward(
            @RequestParam(name = "shopId", required = true) Long shopId, 
            @RequestParam(name = "awardId", required = false) Long awardId, 
            @RequestParam(name = "awardName", required = true) String awardName, 
            @RequestParam(name = "priority", required = true) Integer priority,
            @RequestParam(name = "points", required = true) Long points,
            @RequestParam(name = "awardDesc", required = false) String awardDesc,
            @RequestParam(name = "verifyCodeActual", required = false) String verifyCodeActual,
            @RequestParam(name = "awardImg", required = false) CommonsMultipartFile awardImg,
            HttpSession session) {
        logger.debug("addOrModifyAward - shopId={}, awardId={}, awardName={}, priority={}, points={}, awardDesc={}, verifyCodeActual={}, awardImg={}",
                shopId, awardId, awardName, priority, points, awardDesc, verifyCodeActual, awardImg);
        if(!KaptchaUtil.checkVerifyCode(verifyCodeActual, session)) {
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.INVALID_VERIFY_CODE);
        }

        Award award = new Award();
        award.setAwardDesc(awardDesc);
        award.setAwardId(awardId);
        award.setAwardName(awardName);
        award.setPoints(points);
        award.setPriority(priority);
        award.setShopId(shopId);
        
        ImageHolder awardImgHolder = null;
        try {
            if(awardImg != null) {
                awardImgHolder = new ImageHolder(awardImg);
            }
            if(awardId == null) { //新增
                awardService.addAward(award, awardImgHolder);
            } else { //修改
                awardService.modifyAward(award, awardImgHolder);
            }
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_FAILURE, e);
        } finally {
            try {
                if(null != awardImgHolder) {
                    awardImgHolder.close();
                }
            } catch(Exception e) {
                logger.error(e.getMessage(), e);
                return new OperationResult<Award, AwardOperStateEnum>(AwardOperStateEnum.OPERATION_FAILURE, e);
            }
        }
    }
    
    @GetMapping("/getawarddetail")
    @ResponseBody
    public Result getAwardDetail(@RequestParam(name = "awardId") Long awardId) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("awardDetail", awardService.findAwardById(awardId));
        } catch (Exception e) {
            logger.error("获取奖品详情失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取奖品详情失败");
        }
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
}
