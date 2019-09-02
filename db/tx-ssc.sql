/*
Navicat MySQL Data Transfer

Source Server         : localhost6.5
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : tx-ssc

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-09-02 17:20:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ssc_dingfive_fanfour
-- ----------------------------
DROP TABLE IF EXISTS `ssc_dingfive_fanfour`;
CREATE TABLE `ssc_dingfive_fanfour` (
  `id` bigint(20) NOT NULL,
  `ssc_number` varchar(20) NOT NULL,
  `ssc_shama` varchar(20) DEFAULT NULL,
  `ssc_touma` varchar(20) DEFAULT NULL,
  `ssc_dingwei_count` int(11) DEFAULT NULL COMMENT '投注码定位胆中奖个数',
  `ssc_dingwei_amount` decimal(20,4) DEFAULT NULL COMMENT '定位胆中奖金额',
  `ssc_fanma_result` int(11) DEFAULT NULL COMMENT '杀码中3或4个的中奖情况:1中奖，0未中奖',
  `scc_fanma_amount` decimal(20,4) DEFAULT NULL COMMENT '反码中奖金额',
  `scc_total_amount` decimal(20,4) DEFAULT NULL COMMENT '档期最后利润',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssc_dingfive_fanfour
-- ----------------------------

-- ----------------------------
-- Table structure for ssc_history_data
-- ----------------------------
DROP TABLE IF EXISTS `ssc_history_data`;
CREATE TABLE `ssc_history_data` (
  `id` bigint(20) NOT NULL,
  `ssc_number` varchar(20) NOT NULL,
  `wan` int(11) DEFAULT NULL,
  `qian` int(11) DEFAULT NULL,
  `bai` int(11) DEFAULT NULL,
  `shi` int(11) DEFAULT NULL,
  `ge` int(11) DEFAULT NULL,
  `result` varchar(20) DEFAULT NULL,
  `ssc_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssc_history_data
-- ----------------------------

-- ----------------------------
-- Table structure for ssc_temp_info
-- ----------------------------
DROP TABLE IF EXISTS `ssc_temp_info`;
CREATE TABLE `ssc_temp_info` (
  `id` bigint(32) NOT NULL,
  `ssc_number` varchar(20) NOT NULL,
  `online_number` varchar(20) NOT NULL,
  `online_change` varchar(20) NOT NULL,
  `online_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssc_temp_info
-- ----------------------------
