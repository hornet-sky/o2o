#测试数据
#用户
INSERT INTO `tb_user_info` 
  (`user_id`, `name`, `profile_img`, `email`, `gender`, `enable_status`, `user_type`, `create_time`)
VALUES
  (1,'Jack',NULL,'jack@test.com','1','1','3','2019-02-18 11:24:49',NULL),
  (2,'Tom','https://ws1.sinaimg.cn/large/a15b4afegy1fhsfdznep4j2020020web2.jpg',NULL,'2','1','3','2019-02-24 22:02:57','2019-02-24 22:13:48');

#店铺所属区域
INSERT INTO `tb_area` 
  (`area_id`, `area_name`, `priority`, `create_time`) 
VALUES
  (1, '东苑', 1, NOW()), (2, '西苑', 2, NOW()), (3, '北苑', 3, NOW()), (4, '南苑', 4, NOW()),(5, '暂不填写', 0, NOW()) ;

#店铺类别
INSERT  INTO `tb_shop_category`(`shop_category_id`,`shop_category_name`,`shop_category_desc`,`shop_category_img`,`priority`,`create_time`,`last_edit_time`,`parent_id`) VALUES 
(1,'美食饮品','美食饮品','o2o_webapp/upload/images/shopCategory/2017061223274213433.png',99,'2019-02-18 11:24:49',NULL,NULL),
(2,'美容美发','美容美发','o2o_webapp/upload/images/shopCategory/2017061223273314635.png',98,'2019-02-18 11:24:49',NULL,NULL),
(3,'休闲娱乐','休闲娱乐','o2o_webapp/upload/images/shopCategory/2017061223275121460.png',97,'2019-02-18 11:24:49',NULL,NULL),
(4,'培训教育','培训教育','o2o_webapp/upload/images/shopCategory/2017061223280082147.png',96,'2019-02-18 11:24:49',NULL,NULL),
(5,'二手市场','二手交易市场','o2o_webapp/upload/images/shopCategory/2017061223272255687.png',95,'2019-02-18 11:24:49',NULL,NULL),
(6,'租赁市场','租赁市场','o2o_webapp/upload/images/shopCategory/2017061223281361578.png',94,'2019-02-18 11:24:49',NULL,NULL),
(7,'早餐店',NULL,NULL,1,'2019-02-18 11:24:49',NULL,1),
(8,'奶茶店',NULL,NULL,2,'2019-02-18 11:24:49',NULL,1),
(9,'理发店',NULL,NULL,2,'2019-02-18 11:24:49',NULL,2),
(10,'美容店',NULL,NULL,1,'2019-02-18 11:24:49',NULL,2),
(11,'二手书',NULL,NULL,1,'2019-02-18 11:24:49',NULL,5),
(12,'二手车',NULL,NULL,2,'2019-02-18 11:24:49',NULL,5),
(13,'英语培训',NULL,NULL,2,'2019-02-18 11:24:49',NULL,4),
(14,'IT培训',NULL,NULL,1,'2019-02-18 11:24:49',NULL,4),
(15,'益智棋牌',NULL,NULL,1,'2019-02-18 11:24:49',NULL,3),
(16,'电影院',NULL,NULL,2,'2019-02-18 11:24:49',NULL,3);

#店铺
INSERT  INTO `tb_shop`(`shop_id`,`owner_id`,`area_id`,`shop_category_id`,`shop_name`,`shop_desc`,`shop_addr`,`shop_img`,`phone`,`priority`,`create_time`,`last_edit_time`,`enable_status`,`advice`) VALUES 
(2,1,1,8,'七星茶语','新店开张，欢迎来品尝','东苑102号','o2o_webapp/upload/images/shop/2/201902171931135971179.jpg','1381235678',0,'2019-02-17 19:31:14',NULL,1,NULL),
(3,1,2,8,'旺旺奶茶','台式新品','西苑111号2楼','o2o_webapp/upload/images/shop/3/201902171932102663585.jpg','1351235678',0,'2019-02-17 19:32:10',NULL,1,NULL),
(4,1,2,8,'七喜奶茶','新上珍珠奶茶，欢迎来品尝','西苑123号','o2o_webapp/upload/images/shop/4/201902171933119882304.jpg','1371235678',0,'2019-02-17 19:33:12',NULL,1,NULL),
(5,1,2,7,'一加早餐','营养美味，活力一整天','西苑108号后院','o2o_webapp/upload/images/shop/5/201902171934363816895.jpg','1321235678',0,'2019-02-17 19:34:36',NULL,1,NULL),
(6,1,1,7,'营养早餐','豆浆、包子、牛奶、鸡蛋','东苑176号','o2o_webapp/upload/images/shop/6/201902171935252382663.jpg','1335678987',0,'2019-02-17 19:35:25',NULL,1,NULL),
(7,1,2,7,'校友早餐','早晨高峰时段不送餐，请前来品尝~','西苑110号2楼','o2o_webapp/upload/images/shop/7/201902171936492005781.jpg','13571235678',0,'2019-02-17 19:36:49','2019-02-18 22:19:52',1,NULL),
(8,1,2,9,'北木造型','Tony老师你让high~','西苑前院','o2o_webapp/upload/images/shop/8/201902171939053877212.jpg','1331235677',0,'2019-02-17 19:39:05',NULL,1,NULL),
(9,1,1,10,'YOUNG','一屋子全是总监','东苑路口','o2o_webapp/upload/images/shop/9/201902171940514262239.jpg','13212312312',0,'2019-02-17 19:40:51',NULL,1,NULL),
(10,1,2,11,'小张二手书','欢迎同学们来挑选','西苑侧门','o2o_webapp/upload/images/shop/10/201902171941594452161.jpg','1311235675',0,'2019-02-17 19:41:59',NULL,1,NULL),
(11,1,1,12,'深究二手','只有你想不到的，没有你淘不到的~','东苑111号','o2o_webapp/upload/images/shop/11/201902171943500983621.jpg','1331255678',0,'2019-02-17 19:43:50',NULL,1,NULL),
(12,1,2,13,'新东方','全国最专业的英语培训机构','西苑111号9-12层','o2o_webapp/upload/images/shop/12/201902172003239181393.jpg','1981009876',0,'2019-02-17 20:03:24',NULL,1,NULL),
(13,1,4,12,'优信二手车','您的信任是我们最大的动力','南苑137号','o2o_webapp/upload/images/shop/13/201902181136064207660.jpg','13812356789',0,'2019-02-18 11:36:06',NULL,1,NULL),
(14,1,3,14,'51CTO学院','做最专业的IT教育平台','北苑街道路口','o2o_webapp/upload/images/shop/14/201902181138347494394.jpg','1331239876',0,'2019-02-18 11:38:35',NULL,1,NULL),
(15,1,3,14,'黑马程序员','口碑最好的IT教育平台','北苑前门','o2o_webapp/upload/images/shop/15/201902181139565731424.png','13312565656',0,'2019-02-18 11:39:57',NULL,1,NULL),
(16,1,4,15,'千峰棋牌','休闲放松的最佳选择','南苑后院','o2o_webapp/upload/images/shop/16/201902181141391761052.jpg','13677889900',0,'2019-02-18 11:41:39',NULL,1,NULL),
(17,1,4,16,'星辉电影','暑假特惠','南苑109号','o2o_webapp/upload/images/shop/17/201902181328462516238.jpg','133123556699',0,'2019-02-18 13:28:46',NULL,1,NULL),
(18,1,4,12,'启明二手车','经济实惠','南苑155号院内',NULL,'1381239987',0,'2019-02-19 13:47:39',NULL,1,NULL);

#商品类别
INSERT  INTO `tb_product_category`(`product_category_id`,`product_category_name`,`priority`,`create_time`,`shop_id`) VALUES 
(1,'油条',10,'2019-02-18 21:19:38',7),
(2,'包子',11,'2019-02-18 21:19:38',7),
(3,'豆浆',9,'2019-02-18 21:19:38',7),
(4,'鸡蛋',8,'2019-02-18 21:48:03',7),
(5,'卷饼',7,'2019-02-18 21:48:03',7),
(6,'武功秘籍',0,'2019-02-19 11:25:59',10);

#商品
INSERT  INTO `tb_product`(`product_id`,`product_name`,`product_desc`,`img_addr`,`normal_price`,`promotion_price`,`rewards_points`,`priority`,`create_time`,`last_edit_time`,`enable_status`,`product_category_id`,`shop_id`) VALUES 
(1,'白菜包子','松弹可口，鲜香诱人','o2o_webapp/upload/images/shop/7/201902182128270216286.jpg','3','2.5',2,99,'2019-02-18 21:28:27','2019-02-18 21:30:03',1,2,7),
(2,'牛肉包子','','o2o_webapp/upload/images/shop/7/201902182129319017965.jpg','4','3',3,2,'2019-02-18 21:29:32',NULL,1,2,7),
(3,'羊肉包子','','o2o_webapp/upload/images/shop/7/201902182130519645205.jpg','3.5','3',3,3,'2019-02-18 21:30:52',NULL,1,2,7),
(4,'芹菜包子','','o2o_webapp/upload/images/shop/7/201902182131316949820.jpg','2.5','2',2,4,'2019-02-18 21:31:32',NULL,1,2,7),
(5,'油条','酥脆可口','o2o_webapp/upload/images/shop/7/201902182137026202078.jpg','3','2.5',2,88,'2019-02-18 21:37:03',NULL,1,1,7),
(6,'豆浆（小）','现磨豆浆，营养美味','o2o_webapp/upload/images/shop/7/201902182141403542891.jpg','5','3',3,77,'2019-02-18 21:41:40',NULL,1,3,7),
(7,'豆浆（大）','现磨豆浆，营养美味','o2o_webapp/upload/images/shop/7/201902182142579942076.jpg','6','5',5,76,'2019-02-18 21:42:58',NULL,1,3,7),
(8,'白鸡蛋','滑嫩可口','o2o_webapp/upload/images/shop/7/201902182210594648436.jpg','2',NULL,NULL,10,'2019-02-18 22:10:59',NULL,1,4,7),
(9,'茶叶蛋','鲜香入味','o2o_webapp/upload/images/shop/7/201902182212194051459.jpg','2.5','2',2,15,'2019-02-18 22:12:19',NULL,1,4,7),
(10,'黑椒牛肉卷','非常可口','o2o_webapp/upload/images/shop/7/201902182216040639231.jpg','7','6',6,21,'2019-02-18 22:16:04',NULL,1,5,7),
(11,'茶沙蔬菜卷','美味可口','o2o_webapp/upload/images/shop/7/201902182217315214784.jpg','7','6',6,33,'2019-02-18 22:17:32',NULL,1,5,7),
(12,'土豆丝卷','','o2o_webapp/upload/images/shop/7/201902182218442595182.jpg','6','5',5,32,'2019-02-18 22:18:44',NULL,1,5,7),
(13,'加薪秘籍','加薪必备','o2o_webapp/upload/images/shop/10/201902191224294111043.jpg','66','55',50,22,'2019-02-19 12:24:30',NULL,1,6,10);

#商品详情图
INSERT  INTO `tb_product_img`(`product_img_id`,`img_addr`,`img_desc`,`priority`,`create_time`,`product_id`) VALUES 
(1,'o2o_webapp/upload/images/shop/7/201902182128272101921.jpg',NULL,2,'2019-02-18 21:28:27',1),
(2,'o2o_webapp/upload/images/shop/7/201902182128272955083.jpg',NULL,1,'2019-02-18 21:28:27',1),
(3,'o2o_webapp/upload/images/shop/7/201902182137026743463.jpg',NULL,3,'2019-02-18 21:37:03',5),
(4,'o2o_webapp/upload/images/shop/7/201902182137027325992.jpg',NULL,2,'2019-02-18 21:37:03',5),
(5,'o2o_webapp/upload/images/shop/7/201902182137027898169.jpg',NULL,1,'2019-02-18 21:37:03',5),
(6,'o2o_webapp/upload/images/shop/7/201902182141403926552.jpg',NULL,2,'2019-02-18 21:41:40',6),
(7,'o2o_webapp/upload/images/shop/7/201902182141404356929.jpg',NULL,1,'2019-02-18 21:41:40',6),
(8,'o2o_webapp/upload/images/shop/7/201902182142580438777.jpg',NULL,3,'2019-02-18 21:42:58',7),
(9,'o2o_webapp/upload/images/shop/7/201902182142581066497.jpg',NULL,2,'2019-02-18 21:42:58',7),
(10,'o2o_webapp/upload/images/shop/7/201902182142581517223.jpg',NULL,1,'2019-02-18 21:42:58',7),
(11,'o2o_webapp/upload/images/shop/7/201902182210594933141.jpg',NULL,1,'2019-02-18 22:10:59',8),
(12,'o2o_webapp/upload/images/shop/7/201902182212194525867.jpg',NULL,2,'2019-02-18 22:12:19',9),
(13,'o2o_webapp/upload/images/shop/7/201902182212194989327.jpg',NULL,1,'2019-02-18 22:12:19',9),
(14,'o2o_webapp/upload/images/shop/10/201902191224299426195.jpg',NULL,3,'2019-02-19 12:24:30',13),
(15,'o2o_webapp/upload/images/shop/10/201902191224302471275.jpg',NULL,2,'2019-02-19 12:24:30',13),
(16,'o2o_webapp/upload/images/shop/10/201902191224305208183.jpg',NULL,1,'2019-02-19 12:24:30',13);

#滚动条（头条）
INSERT INTO `o2o`.`tb_head_line` (
  `line_id`, `line_name`, `line_link`, `line_img`, `enable_status`, `priority`, `create_time`, `last_edit_time`
) 
VALUES
  (1, '头条1', 'https://www.baidu.com', 'o2o_webapp/upload/images/headLine/2017061320315746624.jpg', '1', '4', NOW(), NULL),
  (2, '头条2', '', 'o2o_webapp/upload/images/headLine/2017061320371786788.jpg', '1', '3', NOW(), NULL),
  (3, '头条3', '', 'o2o_webapp/upload/images/headLine/2017061320393452772.jpg', '1', '2', NOW(), NULL),
  (4, '头条4', '', 'o2o_webapp/upload/images/headLine/2017061320400198256.jpg', '1', '1', NOW(), NULL);

#本地权限
INSERT INTO `tb_local_auth` (`local_auth_id`, `account`, `password`, `user_id`, `create_time`, `last_edit_time`, `last_login_time`) VALUES('4','tone','rg5hwrr7lo5iigoigri7gr5ho559h65o','1','2019-03-03 22:52:52','2019-03-05 00:07:24',NULL);
INSERT INTO `tb_local_auth` (`local_auth_id`, `account`, `password`, `user_id`, `create_time`, `last_edit_time`, `last_login_time`) VALUES('5','sky','rg5hwrr7lo5iigoigri7gr5ho559h65o','2','2019-05-01 13:56:58',NULL,NULL);
 
#微信权限
INSERT INTO `tb_wechat_auth` (`wechat_auth_id`, `open_id`, `user_id`, `create_time`) VALUES('1','1001','2','2019-02-24 22:03:57');

#奖品
INSERT INTO `tb_award` (`award_id`, `award_name`, `award_desc`, `award_img`, `points`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `shop_id`) VALUES('1','豆沙包','美味可口的豆沙包，是你的早餐首选。','o2o_webapp/upload/images/shop/7/201904061007564171172.jpg','16','9','2019-04-06 10:07:57','2019-04-06 11:30:27','1','7');
INSERT INTO `tb_award` (`award_id`, `award_name`, `award_desc`, `award_img`, `points`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `shop_id`) VALUES('2','南瓜饼','很好吃','o2o_webapp/upload/images/shop/7/201904061026275532323.jpg','15','2','2019-04-06 10:26:28',NULL,'1','7');
INSERT INTO `tb_award` (`award_id`, `award_name`, `award_desc`, `award_img`, `points`, `priority`, `create_time`, `last_edit_time`, `enable_status`, `shop_id`) VALUES('3','豆沙包2','美味可口','o2o_webapp/upload/images/shop/7/201904061131235152021.jpg','10','8','2019-04-06 11:31:24','2019-04-06 11:31:31','0','7');

#积分记录
INSERT INTO `tb_points_record` (
  `record_id`, `consumer_id`, `consumer_name`, `shop_id`, `shop_name`, `product_id`, `product_name`, `points`, `oper_type`, `create_time`, `consumer_visible`, `shopkeeper_visible`, `valid`
) 
VALUES
  (1, 2, 'Tom', 7, '校友早餐', 1, '白菜包子', 2, 1, '2019-03-11 11:20:12', b'1', b'1', b'1'), 
  (2, 2, 'Tom', 7, '校友早餐', 2, '牛肉包子', 3, 1, '2019-03-12 10:10:22', b'1', b'1', b'1'), 
  (3, 2, 'Tom', 7, '校友早餐', 3, '羊肉包子', 3, 1, '2019-04-06 15:21:23', b'1', b'1', b'1'), 
  (4, 1, 'Jack', 7, '校友早餐', 4, '芹菜包子', 2, 1, '2019-02-10 13:20:14', b'1', b'1', b'1'), 
  (5, 2, 'Tom', 7, '校友早餐', 5, '油条包子', 2, 1, '2019-04-05 7:13:27', b'1', b'1', b'1'), 
  (6, 2, 'Tom', 10, '小张二手书', 13, '加薪秘籍', 50, 1, '2019-04-05 12:25:29', b'1', b'1', b'1'), 
  (7, 2, 'Tom', 7, '校友早餐', 1, '豆沙包', 5, -1, '2019-03-15 15:22:27', b'1', b'1', b'1'),
  (8, 2, 'Tom', 7, '校友早餐', 2, '南瓜饼', 4, -1, '2019-04-03 17:55:12', b'1', b'1', b'1'),
  (9, 2, 'Tom', 7, '校友早餐', 2, '牛肉包子', 3, 1, '2019-05-03 16:57:12', b'1', b'1', b'1'),
  (10, 2, 'Tom', 7, '校友早餐', 3, '羊肉包子', 3, 1, '2019-05-03 14:25:13', b'1', b'1', b'1'),
  (11, 2, 'Tom', 7, '校友早餐', 4, '芹菜包子', 2, 1, '2019-05-03 13:33:16', b'1', b'1', b'1'),
  (12, 2, 'Tom', 7, '校友早餐', 5, '油条包子', 2, 1, '2019-05-03 19:11:24', b'1', b'1', b'1'),
  (13, 2, 'Tom', 7, '校友早餐', 1, '豆沙包', 5, -1, '2019-05-03 19:23:19', b'1', b'1', b'1');

#消费记录
INSERT INTO `tb_consumption_record` (
  `record_id`, `consumer_id`, `consumer_name`, `shop_id`, `shop_name`, `product_id`, `product_name`, `expenditure`, `create_time`, `consumer_visible`, `shopkeeper_visible`, `valid`
) 
VALUES
  (1, 2, 'Tom', 7, '校友早餐', 1, '白菜包子', 2.5, '2019-03-11 11:20:12', b'1', b'1', b'1'), 
  (2, 2, 'Tom', 7, '校友早餐', 2, '牛肉包子', 3, '2019-03-12 10:10:22', b'1', b'1', b'1'), 
  (3, 2, 'Tom', 7, '校友早餐', 3, '羊肉包子', 3, '2019-04-06 15:21:23', b'1', b'1', b'1'), 
  (4, 1, 'Jack', 7, '校友早餐', 4, '芹菜包子', 2, '2019-02-10 13:20:14', b'1', b'1', b'1'), 
  (5, 2, 'Tom', 7, '校友早餐', 5, '油条', 2.5, '2019-04-05 7:13:27', b'1', b'1', b'1'), 
  (6, 2, 'Tom', 10, '小张二手书', 13, '加薪秘籍', 50, '2019-04-05 12:25:29', b'1', b'1', b'1'),
  (7, 2, 'Tom', 7, '校友早餐', 5, '油条', 2.5, '2019-04-06 12:35:43', b'1', b'1', b'1'),
  (8, 2, 'Tom', 7, '校友早餐', 2, '牛肉包子', 3, '2019-03-11 11:12:3', b'1', b'1', b'1');
  
  