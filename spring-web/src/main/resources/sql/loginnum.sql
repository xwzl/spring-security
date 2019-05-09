/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : bln_warehouse

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-28 22:13:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for loginnum
-- ----------------------------
DROP TABLE IF EXISTS `loginnum`;
CREATE TABLE `loginnum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `total_login_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of loginnum
-- ----------------------------
