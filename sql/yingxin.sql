/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : yingxin

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-12-08 20:40:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `appointmentdate` date DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ip` varchar(128) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES ('1', '2', '2', '2018-12-01', '4', '5', '6', '7', '8', '9', '2018-12-01 12:25:15', '2018-12-01 12:25:01');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `stages` varchar(1024) DEFAULT NULL,
  `services` varchar(1024) DEFAULT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for project_service_rel
-- ----------------------------
DROP TABLE IF EXISTS `project_service_rel`;
CREATE TABLE `project_service_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projectid` bigint(20) unsigned DEFAULT NULL,
  `serviceid` bigint(20) unsigned DEFAULT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of project_service_rel
-- ----------------------------

-- ----------------------------
-- Table structure for project_stage_rel
-- ----------------------------
DROP TABLE IF EXISTS `project_stage_rel`;
CREATE TABLE `project_stage_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projectid` bigint(20) unsigned DEFAULT NULL,
  `stageid` bigint(20) DEFAULT NULL,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of project_stage_rel
-- ----------------------------

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `createad` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of service
-- ----------------------------

-- ----------------------------
-- Table structure for stage
-- ----------------------------
DROP TABLE IF EXISTS `stage`;
CREATE TABLE `stage` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stage
-- ----------------------------
