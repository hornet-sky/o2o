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
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.HeadLine;
import my.ssm.o2o.entity.LocalAuth;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.HeadLineService;
import my.ssm.o2o.service.LocalService;
import my.ssm.o2o.service.ShopCategoryService;

/**  
 * <p>前端主页面控制器</p>
 * <p>Date: 2019年2月16日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class MainPageController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private LocalService localService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/listmainpageinfo")
    @ResponseBody
    public Result listMainPageInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        HeadLine condition = new HeadLine();
        condition.setEnableStatus(1); //头条状态：0 不可用，1 可用
        try {
            result.put("headLineList", headLineService.findByCondition(condition));
        } catch (Exception e) {
            logger.error("初始化滚动条失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "初始化滚动条失败");
        }
        try {
            result.put("rootCategoryList", shopCategoryService.findRootCategory());
        } catch (Exception e) {
            logger.error("初始化店铺类别失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "初始化店铺类别失败");
        }
        try {
            result.put("resourcesServerContextPath", commonService.getResourcesServerContextPath());
        } catch (Exception e) {
            logger.error("获取资源服务器上下文路径失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取资源服务器上下文路径失败");
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if(userInfo != null) {
            result.put("user", "user");
            LocalAuth localAuth = (LocalAuth) session.getAttribute("localAuth");
            if(localAuth == null) {
                try {
                    localAuth = localService.findLocalAuthByUserId(userInfo.getUserId());
                    session.setAttribute("localAuth", localAuth);
                } catch (Exception e) {
                    logger.error("获取本地授权信息失败", e);
                    new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取本地授权信息失败");
                }
            }
            if(localAuth != null) {
                result.put("account", localAuth.getAccount());
            }
        }
        return new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.OPERATION_SUCCESS, result);
    }
}
