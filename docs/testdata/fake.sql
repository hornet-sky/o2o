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
  (7, '早餐', '营养早餐，让你活力一整天', NULL, 1, NOW(), 1),
  (8, '奶茶', '温馨、浪漫、小清新', NULL, 2, NOW(), 1),
  (9, '北木造型', 'Tony老师让你high', NULL, 2, NOW(), 2),
  (10, '永琪美容美发', '一屋子全是总监', NULL, 1, NOW(), 2);

#滚动条（头条）
INSERT INTO `o2o`.`tb_head_line` (
  `line_id`, `line_name`, `line_link`, `line_img`, `enable_status`, `priority`, `create_time`, `last_edit_time`
) 
VALUES
  (1, '头条1', '', 'o2o_webapp/upload/images/headLine/2017061320315746624.jpg', '1', '4', NOW(), NULL),
  (2, '头条2', '', 'o2o_webapp/upload/images/headLine/2017061320371786788.jpg', '1', '3', NOW(), NULL),
  (3, '头条3', '', 'o2o_webapp/upload/images/headLine/2017061320393452772.jpg', '1', '2', NOW(), NULL),
  (4, '头条4', '', 'o2o_webapp/upload/images/headLine/2017061320400198256.jpg', '1', '1', NOW(), NULL);

