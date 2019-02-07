#数据库mysql 5.7
#创建数据库
CREATE DATABASE IF NOT EXISTS o2o DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE o2o;

#创建区域表
CREATE TABLE IF NOT EXISTS `tb_area` (
  `area_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '区域ID（主键）',
  `area_name` VARCHAR(200) NOT NULL UNIQUE COMMENT '区域名称',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间'
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='区域';

#创建用户信息表
CREATE TABLE IF NOT EXISTS `tb_user_info` (
  `user_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID（主键）',
  `name` VARCHAR(200) NOT NULL COMMENT '用户名',
  `profile_img` VARCHAR(1024) COMMENT '头像',
  `email` VARCHAR(1024) COMMENT '电子邮件',
  `gender` TINYINT(1) COMMENT '性别：0 女，1 男，9 秘密',
  `enable_status` TINYINT(2) NOT NULL COMMENT '用户状态：0 禁止使用本商城，1 允许使用本商城',
  `user_type` TINYINT(2) NOT NULL COMMENT '用户类型：1 顾客，2 店家，9 超级管理员 ',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间'
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息';

#创建微信授权表
CREATE TABLE IF NOT EXISTS `tb_wechat_auth` (
  `wechat_auth_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '微信授权ID（主键）',
  `open_id` VARCHAR(512) NOT NULL UNIQUE COMMENT '用户身份标识',
  `user_id` INT NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
   CONSTRAINT fk_wechat_auth_profile FOREIGN KEY(`user_id`) REFERENCES `tb_user_info`(`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信授权';

#创建本地授权表
CREATE TABLE IF NOT EXISTS `tb_local_auth` (
  `local_auth_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '本地授权ID（主键）',
  `username` VARCHAR(128) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(128) NOT NULL COMMENT '密码',
  `user_id` INT NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
   CONSTRAINT fk_local_auth_profile FOREIGN KEY(`user_id`) REFERENCES `tb_user_info`(`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地授权';

#创建头条表
CREATE TABLE IF NOT EXISTS `tb_head_line` (
  `line_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '头条ID（主键）',
  `line_name` VARCHAR(1000) COMMENT '头条名称',
  `line_link` VARCHAR(2000) NOT NULL COMMENT '头条链接',
  `line_img` VARCHAR(2000) NOT NULL COMMENT '头条图片',
  `enable_status` TINYINT(2) NOT NULL COMMENT '头条状态：0 不可用，1 可用',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间'
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='头条';

#创建店铺类别表
CREATE TABLE IF NOT EXISTS `tb_shop_category` (
  `shop_category_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '店铺类别ID（主键）',
  `shop_category_name` VARCHAR(100) NOT NULL COMMENT '店铺类别名称',
  `shop_category_desc` VARCHAR(1000) COMMENT '店铺类别说明',
  `shop_category_img` VARCHAR(2000) COMMENT '店铺类别图片',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间',
  `parent_id` INT COMMENT '父类别ID',
  CONSTRAINT fk_shop_category_self FOREIGN KEY(`parent_id`) REFERENCES `tb_shop_category`(`shop_category_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺类别';

#创建店铺表
CREATE TABLE IF NOT EXISTS `tb_shop` (
  `shop_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '店铺类别ID（主键）',
  `owner_id` INT NOT NULL COMMENT '店铺创建人ID',
  `area_id` INT COMMENT '店铺所在区域ID',
  `shop_category_id` INT NOT NULL COMMENT '店铺类别ID',
  `shop_name` VARCHAR(256) NOT NULL COMMENT '店铺名称',
  `shop_desc` VARCHAR(1000) COMMENT '店铺说明',
  `shop_addr` VARCHAR(256) COMMENT '店铺地址',
  `shop_img` VARCHAR(1024) COMMENT '店铺图片',
  `phone` VARCHAR(128) COMMENT '电话',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间',
  `enable_status` TINYINT(2) NOT NULL COMMENT '店铺状态：-1 审核不通过，0 审核中，1 审核通过',
  `advice` VARCHAR(255) COMMENT '超级管理员给店家的建议',
  CONSTRAINT fk_shop_profile FOREIGN KEY(`owner_id`) REFERENCES `tb_user_info`(`user_id`),
  CONSTRAINT fk_shop_area FOREIGN KEY(`area_id`) REFERENCES `tb_area`(`area_id`),
  CONSTRAINT fk_shop_shopcate FOREIGN KEY(`shop_category_id`) REFERENCES `tb_shop_category`(`shop_category_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺';

#创建商品类别
CREATE TABLE IF NOT EXISTS `tb_product_category` (
  `product_category_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品类别ID（主键）',
  `product_category_name` VARCHAR(100) NOT NULL COMMENT '商品类别名称',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `shop_id` INT NOT NULL COMMENT '店铺ID',
  CONSTRAINT fk_prodcate_shop FOREIGN KEY(`shop_id`) REFERENCES `tb_shop`(`shop_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品类别';

#创建商品
CREATE TABLE IF NOT EXISTS `tb_product` (
  `product_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID（主键）',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `product_desc` VARCHAR(2000) COMMENT '商品说明',
  `img_addr` VARCHAR(2000) COMMENT '缩略图地址',
  `normal_price` VARCHAR(100) COMMENT '正常价格',
  `promotion_price` VARCHAR(100) COMMENT '促销价格',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_edit_time` DATETIME COMMENT '修改时间',
  `enable_status` TINYINT(2) NOT NULL COMMENT '商品状态：0 下架，1 在前端展示',
  `product_category_id` INT NOT NULL COMMENT '商品类型ID',
  `shop_id` INT NOT NULL COMMENT '店铺ID',
  CONSTRAINT fk_product_prodcate FOREIGN KEY(`product_category_id`) REFERENCES `tb_product_category`(`product_category_id`),
  CONSTRAINT fk_product_shop FOREIGN KEY(`shop_id`) REFERENCES `tb_shop`(`shop_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品';


#创建商品详情图片
CREATE TABLE IF NOT EXISTS `tb_product_img` (
  `product_img_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '图片ID（主键）',
  `img_addr` VARCHAR(2000) NOT NULL COMMENT '图片地址',
  `img_desc` VARCHAR(2000) COMMENT '图片说明',
  `priority` INT NOT NULL COMMENT '权重（影响展示顺序）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `product_id` INT COMMENT '商品ID',
  CONSTRAINT fk_prodimg_product FOREIGN KEY(`product_id`) REFERENCES `tb_product`(`product_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品详情图片';


