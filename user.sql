/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : test_user

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-09-09 17:40:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '12134', '123699996666', '2020-09-09 16:28:25');
INSERT INTO `user` VALUES ('2', 'spring', '123456', '13698746589', '2020-09-09 16:28:28');
INSERT INTO `user` VALUES ('3', 'test', '12134', '123699996666', '2020-09-09 16:28:25');
INSERT INTO `user` VALUES ('4', 'springboot', '123456', '13698746589', '2020-09-09 16:28:28');
