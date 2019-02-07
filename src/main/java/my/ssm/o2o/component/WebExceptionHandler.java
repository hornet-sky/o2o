package my.ssm.o2o.component;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import my.ssm.o2o.dto.OperationResult;
import my.ssm.o2o.dto.Result;
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.util.CommonUtil;
import my.ssm.o2o.util.ExceptionUtil;

/**  
 * <p>异常处理器</p>
 * <p>Date: 2019年2月7日</p>
 * @author Wanghui    
 */  
@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);
    @Value("#{prop['upload.maxUploadSize']}")
    private Long maxUploadSize;
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error("服务器异常", e);
        if(e instanceof MaxUploadSizeExceededException) {
            Throwable rootCause = ExceptionUtil.getRootCause(e);
            if(rootCause instanceof SizeLimitExceededException) {
                String msg = "照片大小不能超过 " + CommonUtil.humanizeByteSize(maxUploadSize);
                return new OperationResult<Object, CommonOperStateEnum>(CommonOperStateEnum.INTERNAL_SERVER_ERROR.getState(), msg);
            }
        }
        return new OperationResult<Object, CommonOperStateEnum>(CommonOperStateEnum.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}