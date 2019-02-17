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
  (1, '东苑', 1, NOW()), (2, '西苑', 2, NOW()),(3, '暂不填写', 0, NOW()) ;

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
  (12, '二手用品', NULL, NULL, 2, NOW(), 5),
  (13, '英语培训', NULL, NULL, 1, NOW(), 4);
  
#店铺
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('2','1','1','8','七星茶语','新店开张，欢迎来品尝','东苑102号','o2o_webapp/upload/images/shop/2/201902171931135971179.jpg','1381235678','0','2019-02-17 19:31:14',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('3','1','2','8','旺旺奶茶','','西苑111号2楼','o2o_webapp/upload/images/shop/3/201902171932102663585.jpg','1351235678','0','2019-02-17 19:32:10',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('4','1','2','8','七喜奶茶','新上珍珠奶茶，欢迎来品尝','西苑123号','o2o_webapp/upload/images/shop/4/201902171933119882304.jpg','1371235678','0','2019-02-17 19:33:12',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('5','1','2','7','一加早餐','营养美味，活力一整天','西苑108号后院','o2o_webapp/upload/images/shop/5/201902171934363816895.jpg','1321235678','0','2019-02-17 19:34:36',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('6','1','1','7','营养早餐','','东苑176号','o2o_webapp/upload/images/shop/6/201902171935252382663.jpg','1335678987','0','2019-02-17 19:35:25',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('7','1','2','7','校友早餐','','西苑110号2楼','o2o_webapp/upload/images/shop/7/201902171936492005781.jpg','13571235678','0','2019-02-17 19:36:49',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('8','1','2','9','北木造型','Tony老师你让high~','西苑前院','o2o_webapp/upload/images/shop/8/201902171939053877212.jpg','1331235677','0','2019-02-17 19:39:05',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('9','1','1','10','YOUNG','一屋子全是总监','东苑路口','o2o_webapp/upload/images/shop/9/201902171940514262239.jpg','13212312312','0','2019-02-17 19:40:51',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('10','1','2','11','小张二手书','欢迎同学们来挑选','西苑侧门','o2o_webapp/upload/images/shop/10/201902171941594452161.jpg','1311235675','0','2019-02-17 19:41:59',NULL,'1',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('11','1','1','12','深究二手','只有你想不到的，没有你淘不到的~','东苑111号','o2o_webapp/upload/images/shop/11/201902171943500983621.jpg','1331255678','0','2019-02-17 19:43:50',NULL,'0',NULL);
INSERT INTO `tb_shop` (`shop_id`, `owner_id`, `area_id`, `shop_category_id`, `shop_name`, `shop_desc`, `shop_addr`, `shop_img`, `phone`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `advice`) VALUES('12','1','2','13','新东方','','西苑111','o2o_webapp/upload/images/shop/12/201902172003239181393.jpg','1981009876','0','2019-02-17 20:03:24',NULL,'0',NULL);


#滚动条（头条）
INSERT INTO `o2o`.`tb_head_line` (
  `line_id`, `line_name`, `line_link`, `line_img`, `enable_status`, `priority`, `create_time`, `last_edit_time`
) 
VALUES
  (1, '头条1', 'https://www.baidu.com', 'o2o_webapp/upload/images/headLine/2017061320315746624.jpg', '1', '4', NOW(), NULL),
  (2, '头条2', '', 'o2o_webapp/upload/images/headLine/2017061320371786788.jpg', '1', '3', NOW(), NULL),
  (3, '头条3', '', 'o2o_webapp/upload/images/headLine/2017061320393452772.jpg', '1', '2', NOW(), NULL),
  (4, '头条4', '', 'o2o_webapp/upload/images/headLine/2017061320400198256.jpg', '1', '1', NOW(), NULL);

