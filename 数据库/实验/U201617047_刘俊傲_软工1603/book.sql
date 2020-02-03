/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : book

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2019-01-04 14:11:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for allbook
-- ----------------------------
DROP TABLE IF EXISTS `allbook`;
CREATE TABLE `allbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `num` int(11) NOT NULL COMMENT '书号',
  `name` varchar(255) DEFAULT NULL COMMENT '书名',
  `author` varchar(255) DEFAULT NULL COMMENT '作者，最多四个，有序',
  `publish` varchar(255) DEFAULT NULL COMMENT '出版社',
  `money` int(11) DEFAULT NULL COMMENT '价格',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键字',
  `dir` text COMMENT '书记目录',
  `cover` blob COMMENT '书记封面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='所有书籍相关信息，对应供应商提供的书籍信息';

-- ----------------------------
-- Table structure for missbook
-- ----------------------------
DROP TABLE IF EXISTS `missbook`;
CREATE TABLE `missbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `num` int(11) NOT NULL COMMENT '书号',
  `name` varchar(255) NOT NULL COMMENT '书名',
  `publish` varchar(255) NOT NULL COMMENT '出版社',
  `providerid` varchar(255) NOT NULL COMMENT '供应商ID',
  `miss` int(2) NOT NULL COMMENT '是否缺失：\r\n0： 不缺失\r\n1： 缺失',
  `date` datetime DEFAULT NULL COMMENT '登记日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于记录书籍存货量低于一定数量的书籍信息';

-- ----------------------------
-- Table structure for missuser
-- ----------------------------
DROP TABLE IF EXISTS `missuser`;
CREATE TABLE `missuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '缺货用户ID，对应 storeuser 表',
  `username` varchar(255) DEFAULT NULL COMMENT '缺货用户名',
  `userbooknum` int(11) DEFAULT NULL COMMENT '缺货书籍的书号',
  `count` int(11) DEFAULT NULL COMMENT '缺书的数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录下顾客的缺书请求以便答复';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单号，主键',
  `date` datetime NOT NULL COMMENT '订货日期',
  `userid` int(11) DEFAULT NULL COMMENT '客户ID',
  `money` int(11) DEFAULT NULL COMMENT '金额',
  `orderbyid` int(255) DEFAULT NULL COMMENT '对应 orderbyid 表中的 ID',
  `address` varchar(255) DEFAULT NULL COMMENT '发货地址',
  `condition` int(2) DEFAULT NULL COMMENT '发货情况：\r\n0： 未发货\r\n1： 已发货，但客户还未收到\r\n2： 已发货，且客户已收到',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订单信息';

-- ----------------------------
-- Table structure for orderbyid
-- ----------------------------
DROP TABLE IF EXISTS `orderbyid`;
CREATE TABLE `orderbyid` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bookid` int(11) DEFAULT NULL COMMENT '书号',
  `num` int(11) DEFAULT NULL COMMENT '该本书预定的数量',
  `money` int(11) DEFAULT NULL COMMENT '该类型书的总价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于存储同一用户的订单信息，可同时预订多本书籍';

-- ----------------------------
-- Table structure for provider
-- ----------------------------
DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '供应商姓名',
  `address` varchar(255) DEFAULT NULL COMMENT '供应商地址',
  `allbookid` int(11) DEFAULT NULL COMMENT '供应商所拥有的书籍对应的 allbook 表信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商的相关信息';

-- ----------------------------
-- Table structure for storebook
-- ----------------------------
DROP TABLE IF EXISTS `storebook`;
CREATE TABLE `storebook` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `num` int(11) NOT NULL COMMENT '书号',
  `name` varchar(255) NOT NULL COMMENT '书名',
  `author` varchar(255) NOT NULL COMMENT '作者，最多4个，有序',
  `publish` varchar(255) NOT NULL COMMENT '出版社',
  `money` int(11) NOT NULL COMMENT '价格',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键字',
  `dir` text COMMENT '书籍目录',
  `cover` blob COMMENT '封面',
  `count` int(11) NOT NULL COMMENT '存货量',
  `providerid` varchar(255) DEFAULT NULL COMMENT '供应商ID，可有多个',
  `userid` int(11) DEFAULT NULL COMMENT '对应 storeuser 中的 id，用于记录当前或最近订书的用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对应于网站书籍库存';

-- ----------------------------
-- Table structure for storeuser
-- ----------------------------
DROP TABLE IF EXISTS `storeuser`;
CREATE TABLE `storeuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '网上 id，主键',
  `name` varchar(255) NOT NULL COMMENT '客户名',
  `password` varchar(255) NOT NULL COMMENT '登陆密码',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `balance` int(11) NOT NULL COMMENT '余额',
  `debt` int(11) DEFAULT NULL COMMENT '欠款：等级三、四有限额，等级五无限额',
  `grade` int(2) NOT NULL COMMENT '信用等级：\r\n一级：10% 折扣，不能透支\r\n二级：15% 折扣，不能透支\r\n三级：15% 折扣，透支限额，可先发书再付款\r\n四级：20% 折扣，透支限额，可先发书再付款\r\n五级：25% 折扣，透支无限额，可先发书再付款',
  `moneyCount` int(11) DEFAULT NULL COMMENT '累计充值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存用户的相关信息';

-- ----------------------------
-- Procedure structure for checkGrade
-- ----------------------------
DROP PROCEDURE IF EXISTS `checkGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkGrade`(in grade INT,in money INT,in uid INT,in grade3 INT,in grade4 INT)
BEGIN
	SET @balance=(SELECT balance FROM storeuser WHERE id=uid);
	CASE
		WHEN grade=1 THEN 
			UPDATE storeuser SET balance=@balance-money*0.9;
		WHEN grade=2 THEN
			UPDATE storeuser SET balance=@balance-money*0.85;
		WHEN grade=3 THEN
			IF(@balance < @tmp=money*0.85)
			THEN
				SET @tmp=money*0.85 - @balance;
				IF(grade3 > @tmp)
				THEN
					UPDATE storeuser SET balance=0;
					UPDATE storeuser SET debt=@tmp;
				END IF;
			ELSE UPDATE storeuser SET balance=@balance-money*0.85;
			END IF;
		WHEN grade=4 THEN
			IF(@balance < @tmp=money*0.8)
			THEN
				SET @tmp=money*0.8 - @balance;
				IF(grade3 > @tmp)
				THEN
					UPDATE storeuser SET balance=0;
					UPDATE storeuser SET debt=@tmp;
				END IF;
			ELSE UPDATE storeuser SET balance=@balance-money*0.8;
			END IF;
		WHEN grade=5 THEN
			IF(@balance < @tmp=money*0.75)
			THEN
				SET @tmp=money*0.75 - @balance;
				UPDATE storeuser SET balance=0;
				UPDATE storeuser SET debt=@tmp;
			ELSE UPDATE storeuser SET balance=@balance-money*0.75;
			END IF;
	END CASE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for insertNewBook
-- ----------------------------
DROP PROCEDURE IF EXISTS `insertNewBook`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertNewBook`(in uid INT(11),in bnum INT(11))
BEGIN
	set @uname = (SELECT name from storeuser WHERE id=uid);
	INSERT INTO missuser(userid,username,userbooknum,count)
	VALUES(uid,@uname,bnum,1);
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insertNewBook`;
DELIMITER ;;
CREATE TRIGGER `insertNewBook` AFTER INSERT ON `storebook` FOR EACH ROW BEGIN
	INSERT INTO missbook(num,name,publish,providerid,miss,date) 
	VALUES(NEW.num,NEW.name,NEW.publish,NEW.providerid,0,NOW());
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `misscount`;
DELIMITER ;;
CREATE TRIGGER `misscount` AFTER UPDATE ON `storebook` FOR EACH ROW BEGIN
	IF(NEW.count < 5)
	THEN
	UPDATE missbook SET miss=1,date=NOW() WHERE num=OLD.num;
	END IF;
END
;;
DELIMITER ;
