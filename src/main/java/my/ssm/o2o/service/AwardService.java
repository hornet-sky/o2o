package my.ssm.o2o.service;

import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Award;

/**  
 * <p>奖品服务接口</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
public interface AwardService {
    /**  
     * <p>查询指定ID的奖品信息</p>  
     * @param awardId 奖品ID
     * @return  奖品信息
     */  
    Award findAwardById(Long awardId);
    /**  
     * <p>查找指定条件及分页参数的奖品信息</p>  
     * @param condition 查询条件
     * @param searchKey 查询关键字
     * @param pagingParams 分页参数
     * @return  符合条件的奖品信息集合
     */  
    PagingResult<Award> listAward(Award condition, String searchKey, PagingParams pagingParams);
    /**  
     * <p>上架或下架指定奖品</p>  
     * @param awardId 待操作的奖品ID
     * @param enableStatus 最终设定的奖品状态，0 下架，1 在前端展示。
     * @return  受影响的记录条数
     */  
    int shelveAward(Long awardId, Integer enableStatus);
    /**  
     * <p>添加奖品</p>  
     * @param award 待添加的奖品
     * @param imgHolder 奖品图片
     */  
    void addAward(Award award, ImageHolder imgHolder);
    /**  
     * <p>修改奖品</p>  
     * @param award 待修改的奖品
     * @param imgHolder 奖品图片
     */  
    void modifyAward(Award award, ImageHolder imgHolder);
    /**  
     * <p>添加奖品图片</p>  
     * @param shopId 店铺ID，主要用于生成存放图片的目录
     * @param image 待添加的奖品图片
     * @return 奖品图片的相对路径 
     */  
    String addImg(Long shopId, ImageHolder image);
    /**  
     * <p>更新奖品图片</p>  
     * @param shopId 店铺ID，主要用于生成存放图片的目录
     * @param awardId 奖品ID
     * @param image 新的奖品图片
     * @return 新的奖品图片的相对路径
     */  
    String updateImg(Long shopId, Long awardId, ImageHolder image);
}
