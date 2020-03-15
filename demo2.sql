/*
Navicat MySQL Data Transfer

Source Server         : aliyun_myMysql01_jiweihao_3306_5.5
Source Server Version : 50562
Source Host           : 60.205.214.42:3306
Source Database       : demo2

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-03-15 13:50:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL COMMENT '昵称',
  `gender` varchar(1) NOT NULL DEFAULT '2' COMMENT '性别,1为男,0为女,2为保密',
  `emall` varchar(30) NOT NULL COMMENT '邮箱号,作为主账号使用',
  `phone_number` varchar(30) NOT NULL COMMENT '手机号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone_number`) USING BTREE,
  UNIQUE KEY `emall` (`emall`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'jiweihao', '0', '1141517977@qq.com', '110111');
INSERT INTO `user` VALUES ('4', 'zhangsan', '0', '7977@qq.com', '112111');
