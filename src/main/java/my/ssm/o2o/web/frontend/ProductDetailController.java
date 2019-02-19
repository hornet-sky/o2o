package my.ssm.o2o.web.frontend;

import java.util.HashMap;
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
import my.ssm.o2o.enums.CommonOperStateEnum;
import my.ssm.o2o.service.CommonService;
import my.ssm.o2o.service.ProductService;

/**  
 * <p>商品详情控制器</p>
 * <p>Date: 2019年2月19日</p>
 * @author Wanghui    
 */  
@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductService productService;
    @Autowired
    private CommonService commonService;
    
    @GetMapping("/getproductdetail")
    @ResponseBody
    public Result getProductDetail(@RequestParam(name = "productId") Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("productDetail", productService.findOverloadedProductById(productId));
        } catch (Exception e) {
            logger.error("获取商品详情失败", e);
            new OperationResult<Map<String, Object>, CommonOperStateEnum>(CommonOperStateEnum.INITIALIZATION_FAILURE.getState(), "获取商品详情失败");
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
