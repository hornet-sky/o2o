package my.ssm.o2o.web.local;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.entity.LocalAuth;
import my.ssm.o2o.entity.UserInfo;
import my.ssm.o2o.enums.LocalOperStateEnum;
import my.ssm.o2o.service.LocalService;
import my.ssm.o2o.util.KaptchaUtil;

/**  
 * <p>本地授权管理模块控制器</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/local")
public class LocalManagementController {
    private final String accountRegExp = "^\\w{3,}$";
    private final String passwordRegExp = "^[\\x20-\\x7E]{6,}$";
    private final Logger logger = LoggerFactory.getLogger(LocalManagementController.class);
    @Autowired
    private LocalService localService;
    @PostMapping("/createandbindauth")
    @ResponseBody
    public Result createAndBindAuth(String account, String password, String kaptch, HttpSession session) {
        if(!KaptchaUtil.checkVerifyCode(kaptch, session)) {
            return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.INVALID_VERIFY_CODE);
        }
        if(account == null || !Pattern.matches(accountRegExp, account)) {
            return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.LEGAL_ACCOUNT);
        }
        if(password == null || !Pattern.matches(passwordRegExp, password)) {
            return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.LEGAL_PASSWORD);
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        Long userId = userInfo.getUserId();
        try {
            if(localService.existsLocalAuth(userId)) {
                return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.REPETITIVE_LOCAL_AUTH);
            }
            localService.createAndBindLocalAuth(account, password, userInfo);
            return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.BIND_SUCCESS);
        } catch (Exception e) {
            logger.error("创建并绑定本地授权期间产生了异常", e);
            return new OperationResult<LocalAuth, LocalOperStateEnum>(LocalOperStateEnum.OPERATION_FAILURE.getState(), "创建并绑定本地授权期间产生了异常");
        }
    }
    
}
