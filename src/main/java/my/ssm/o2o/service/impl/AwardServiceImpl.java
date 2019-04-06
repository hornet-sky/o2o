package my.ssm.o2o.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.ssm.o2o.dao.AwardDao;
import my.ssm.o2o.dto.ImageHolder;
import my.ssm.o2o.dto.PagingParams;
import my.ssm.o2o.dto.PagingResult;
import my.ssm.o2o.entity.Award;
import my.ssm.o2o.enums.AwardOperStateEnum;
import my.ssm.o2o.enums.Direction;
import my.ssm.o2o.exception.AwardOperationException;
import my.ssm.o2o.exception.ProductOperationException;
import my.ssm.o2o.service.AwardService;
import my.ssm.o2o.util.ImageUtil;
import my.ssm.o2o.util.PathUtil;

/**  
 * <p>奖品服务接口实现类</p>
 * <p>Date: 2019年4月5日</p>
 * @author Wanghui    
 */  
@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardDao awardDao;
    @Override
    public Award findAwardById(Long awardId) {
        return awardDao.findById(awardId);
    }
    @Override
    public PagingResult<Award> listAward(Award condition, String searchKey, PagingParams pagingParams) {
        if(pagingParams.isOrderRuleMapEmpty()) { //默认按创建顺序降序排列
            pagingParams.addOrderRule("award_id", Direction.DESC);
        }
        List<Award> list = awardDao.list(condition, searchKey, pagingParams);
        long count = awardDao.count(condition, searchKey);
        return new PagingResult<Award>(list, count);
    }
    @Transactional
    @Override
    public int shelveAward(Long awardId, Integer enableStatus) {
        Award award = awardDao.findById(awardId);
        if(award == null) {
            throw new AwardOperationException("所" + (AwardOperStateEnum.UNSHELVE.getState().equals(enableStatus) ? "下架" : "上架") + "的奖品不存在");
        }
        Award condition = new Award();
        condition.setEnableStatus(enableStatus);
        condition.setLastEditTime(new Date());
        condition.setAwardId(awardId);
        return awardDao.update(condition);
    }
    @Transactional
    @Override
    public void addAward(Award award, ImageHolder img) {
        Long shopId = award.getShopId();
        //1、保存奖品图片
        String thumbnailRelativePath = null;
        if(img != null) {
            try {
                thumbnailRelativePath = addImg(shopId, img);
                award.setAwardImg(thumbnailRelativePath);
            } catch (Exception e) {
                throw new ProductOperationException("保存奖品图片失败", e);
            }
        }
        //2、保存奖品信息
        Date now = new Date();
        award.setCreateTime(now);
        award.setEnableStatus(AwardOperStateEnum.SHELVE.getState());
        try {
            awardDao.save(award);
        } catch (Exception e) {
            //删除已保存的奖品图片
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            throw new ProductOperationException("保存奖品信息失败", e);
        }
    }
    @Transactional
    @Override
    public void modifyAward(Award award, ImageHolder img) {
        Long shopId = award.getShopId();
        Long awardId = award.getAwardId();
        //1、更新奖品图片
        String thumbnailRelativePath = null;
        if(img != null) {
            try {
                thumbnailRelativePath = updateImg(shopId, awardId, img);
                award.setAwardImg(thumbnailRelativePath);
            } catch (Exception e) {
                throw new ProductOperationException("更新奖品图片失败", e);
            }
        }
        //2、更新奖品信息
        Date now = new Date();
        award.setLastEditTime(now);
        try {
            awardDao.update(award);
        } catch (Exception e) {
            //删除已更新的缩略图
            if(StringUtils.isNotBlank(thumbnailRelativePath)) {
                ImageUtil.remove(thumbnailRelativePath);
            }
            throw new ProductOperationException("更新奖品信息失败", e);
        }
    }
    @Transactional
    @Override
    public String addImg(Long shopId, ImageHolder image) {
        try {
            return ImageUtil.generateCoverImage(image,
                    PathUtil.getShopImageRelativeDirPath(shopId));
        } catch (IOException e) {
            throw new ProductOperationException(e);
        }
    }
    @Transactional
    @Override
    public String updateImg(Long shopId, Long awardId, ImageHolder image) {
        try {
            String oldRelativePath = awardDao.findById(awardId).getAwardImg();
            ImageUtil.remove(oldRelativePath);
        } catch (Exception e) {
            throw new AwardOperationException("删除旧的奖品图片失败");
        }
        return addImg(shopId, image);
    }
}
