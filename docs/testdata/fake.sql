#测试数据
#用户
INSERT INTO `o2o`.`tb_user_info` 
  (`user_id`, `name`, `profile_img`, `email`, `gender`, `enable_status`, `user_type`, `create_time`)
VALUES
  (1, 'Jack', NULL, 'jack@test.com', 1, 1, 2, NOW()) ;

#店铺所属区域
INSERT INTO `o2o`.`tb_area` 
  (`area_id`, `area_name`, `priority`, `create_time`) 
VALUES
  (1, '东苑', 1, NOW()), (2, '西苑', 2, NOW()), (3, '北苑', 3, NOW()), (4, '南苑', 4, NOW()),(5, '暂不填写', 0, NOW()) ;

#店铺类别
INSERT INTO `o2o`.`tb_shop_category` 
  (`shop_category_id`, `shop_category_name`, `shop_category_desc`, `shop_category_img`, `priority`, `create_time`, `parent_id`) 
VALUES
  (1, '美食饮品', '美食饮品', 'o2o_webapp/upload/images/shopCategory/2017061223274213433.png', 99, NOW(), NULL),
  (2, '美容美发', '美容美发', 'o2o_webapp/upload/images/shopCategory/2017061223273314635.png', 98, NOW(), NULL),
  (3, '休闲娱乐', '休闲娱乐', 'o2o_webapp/upload/images/shopCategory/2017061223275121460.png', 97, NOW(), NULL),
  (4, '培训教育', '培训教育', 'o2o_webapp/upload/images/shopCategory/2017061223280082147.png', 96, NOW(), NULL),
  (5, '二手市场', '二手交易市场', 'o2o_webapp/upload/images/shopCategory/2017061223272255687.png', 95, NOW(), NULL),
  (6, '租赁市场', '租赁市场', 'o2o_webapp/upload/images/shopCategory/2017061223281361578.png', 94, NOW(), NULL),
  (7, '早餐店', NULL, NULL, 1, NOW(), 1),
  (8, '奶茶店', NULL, NULL, 2, NOW(), 1),
  (9, '理发店', NULL, NULL, 2, NOW(), 2),
  (10, '美容店', NULL, NULL, 1, NOW(), 2),
  (11, '二手书', NULL, NULL, 1, NOW(), 5),
  (12, '二手车', NULL, NULL, 2, NOW(), 5),
  (13, '英语培训', NULL, NULL, 2, NOW(), 4),
  (14, 'IT培训', NULL, NULL, 1, NOW(), 4),
  (15, '益智棋牌', NULL, NULL, 1, NOW(), 3),
  (16, '电影院', NULL, NULL, 2, NOW(), 3);
  
#店铺
INSERT  INTO `tb_shop`(`shop_id`,`owner_id`,`area_id`,`shop_category_id`,`shop_name`,`shop_desc`,`shop_addr`,`shop_img`,`phone`,`priority`,`create_time`,`last_edit_time`,`enable_status`,`advice`) VALUES 
(2,1,1,8,'七星茶语','新店开张，欢迎来品尝','东苑102号','o2o_webapp/upload/images/shop/2/201902171931135971179.jpg','1381235678',0,'2019-02-17 19:31:14',NULL,1,NULL),
(3,1,2,8,'旺旺奶茶','台式新品','西苑111号2楼','o2o_webapp/upload/images/shop/3/201902171932102663585.jpg','1351235678',0,'2019-02-17 19:32:10',NULL,1,NULL),
(4,1,2,8,'七喜奶茶','新上珍珠奶茶，欢迎来品尝','西苑123号','o2o_webapp/upload/images/shop/4/201902171933119882304.jpg','1371235678',0,'2019-02-17 19:33:12',NULL,1,NULL),
(5,1,2,7,'一加早餐','营养美味，活力一整天','西苑108号后院','o2o_webapp/upload/images/shop/5/201902171934363816895.jpg','1321235678',0,'2019-02-17 19:34:36',NULL,1,NULL),
(6,1,1,7,'营养早餐','豆浆、包子、牛奶、鸡蛋','东苑176号','o2o_webapp/upload/images/shop/6/201902171935252382663.jpg','1335678987',0,'2019-02-17 19:35:25',NULL,1,NULL),
(7,1,2,7,'校友早餐','早晨高峰时段不送餐','西苑110号2楼','o2o_webapp/upload/images/shop/7/201902171936492005781.jpg','13571235678',0,'2019-02-17 19:36:49',NULL,1,NULL),
(8,1,2,9,'北木造型','Tony老师你让high~','西苑前院','o2o_webapp/upload/images/shop/8/201902171939053877212.jpg','1331235677',0,'2019-02-17 19:39:05',NULL,1,NULL),
(9,1,1,10,'YOUNG','一屋子全是总监','东苑路口','o2o_webapp/upload/images/shop/9/201902171940514262239.jpg','13212312312',0,'2019-02-17 19:40:51',NULL,1,NULL),
(10,1,2,11,'小张二手书','欢迎同学们来挑选','西苑侧门','o2o_webapp/upload/images/shop/10/201902171941594452161.jpg','1311235675',0,'2019-02-17 19:41:59',NULL,1,NULL),
(11,1,1,12,'深究二手','只有你想不到的，没有你淘不到的~','东苑111号','o2o_webapp/upload/images/shop/11/201902171943500983621.jpg','1331255678',0,'2019-02-17 19:43:50',NULL,1,NULL),
(12,1,2,13,'新东方','全国最专业的英语培训机构','西苑111号9-12层','o2o_webapp/upload/images/shop/12/201902172003239181393.jpg','1981009876',0,'2019-02-17 20:03:24',NULL,1,NULL),
(13,1,4,12,'优信二手车','您的信任是我们最大的动力','南苑137号','o2o_webapp/upload/images/shop/13/201902181136064207660.jpg','13812356789',0,'2019-02-18 11:36:06',NULL,1,NULL),
(14,1,3,14,'51CTO学院','做最专业的IT教育平台','北苑街道路口','o2o_webapp/upload/images/shop/14/201902181138347494394.jpg','1331239876',0,'2019-02-18 11:38:35',NULL,1,NULL),
(15,1,3,14,'黑马程序员','口碑最好的IT教育平台','北苑前门','o2o_webapp/upload/images/shop/15/201902181139565731424.png','13312565656',0,'2019-02-18 11:39:57',NULL,1,NULL),
(16,1,4,15,'千峰棋牌','休闲放松的最佳选择','南苑后院','o2o_webapp/upload/images/shop/16/201902181141391761052.jpg','13677889900',0,'2019-02-18 11:41:39',NULL,1,NULL),
(17,1,4,16,'星辉电影','暑假特惠','南苑109号','o2o_webapp/upload/images/shop/17/201902181328462516238.jpg','133123556699',0,'2019-02-18 13:28:46',NULL,1,NULL);

#滚动条（头条）
INSERT INTO `o2o`.`tb_head_line` (
  `line_id`, `line_name`, `line_link`, `line_img`, `enable_status`, `priority`, `create_time`, `last_edit_time`
) 
VALUES
  (1, '头条1', 'https://www.baidu.com', 'o2o_webapp/upload/images/headLine/2017061320315746624.jpg', '1', '4', NOW(), NULL),
  (2, '头条2', '', 'o2o_webapp/upload/images/headLine/2017061320371786788.jpg', '1', '3', NOW(), NULL),
  (3, '头条3', '', 'o2o_webapp/upload/images/headLine/2017061320393452772.jpg', '1', '2', NOW(), NULL),
  (4, '头条4', '', 'o2o_webapp/upload/images/headLine/2017061320400198256.jpg', '1', '1', NOW(), NULL);

