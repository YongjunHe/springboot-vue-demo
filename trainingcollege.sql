/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : trainingcollege

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-04 12:55:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `orderid` int(6) NOT NULL,
  `courseid` int(5) NOT NULL,
  `semail` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`orderid`,`semail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('100000', '10000', '141250001@qq.com', '85');
INSERT INTO `classes` VALUES ('100000', '10000', '141250002@qq.com', '0');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `id` int(7) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `finance` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1010116 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES ('1000000', '123456', 'Beijing', '102000', '1');
INSERT INTO `college` VALUES ('1000001', '123456', 'Nanjing', '100000', '0');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `college` int(7) NOT NULL,
  `type` varchar(255) NOT NULL,
  `temail` varchar(255) NOT NULL,
  `size` int(11) NOT NULL,
  `period` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10020 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('10000', '1000000', 'math', '141260001@qq.com', '50', '8', '1000');
INSERT INTO `course` VALUES ('10016', '1000001', 'coding', '141260002@qq.com', '50', '8', '1000');
INSERT INTO `course` VALUES ('10018', '1000000', 'language', '141260001@qq.com', '50', '8', '1000');
INSERT INTO `course` VALUES ('10019', '1000000', 'language', '141260001@qq.com', '100', '6', '1000');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `college` int(7) NOT NULL,
  `type` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `creattime` datetime DEFAULT NULL,
  `paytime` datetime DEFAULT NULL,
  `settletime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100030 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('100000', '141250001@qq.com', '1000000', 'math', '2000', '2', '2018-04-03 01:51:31', '2018-04-03 01:51:37', '2018-04-04 01:51:43');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `courseid` int(5) NOT NULL AUTO_INCREMENT,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  PRIMARY KEY (`courseid`,`starttime`)
) ENGINE=InnoDB AUTO_INCREMENT=10020 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES ('10000', '2018-03-01 17:30:22', '2018-03-01 19:30:22');
INSERT INTO `schedule` VALUES ('10000', '2018-03-08 17:30:26', '2018-03-08 19:30:26');
INSERT INTO `schedule` VALUES ('10000', '2018-03-15 17:30:28', '2018-03-15 19:30:28');
INSERT INTO `schedule` VALUES ('10000', '2018-03-22 17:30:29', '2018-03-22 19:30:29');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `college` int(7) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('141260001@qq.com', 'ekko', '123456', 'teacher', '1000000');
INSERT INTO `staff` VALUES ('141260002@qq.com', 'eugene', '123456', 'manager', '1000001');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `level` int(11) NOT NULL,
  `deposit` int(11) NOT NULL,
  `point` int(11) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('141250001@qq.com', 'ben', '123456', '1', '10400', '600');
INSERT INTO `student` VALUES ('141250002@qq.com', 'harry', '123456', '1', '20000', '2000');
INSERT INTO `student` VALUES ('141250003@qq.com', 'lucy', '123456', '0', '30000', '30000');

-- ----------------------------
-- Table structure for verification
-- ----------------------------
DROP TABLE IF EXISTS `verification`;
CREATE TABLE `verification` (
  `email` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of verification
-- ----------------------------
