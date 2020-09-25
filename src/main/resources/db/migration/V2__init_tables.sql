
-- ----------------------------
--  Table structure for `ticket`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '工单ID',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单名称',
  `creator` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '发起人',
  `time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `form_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '表单ID',
  `model_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程模型ID',
  `config_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单模板ID',
  `process_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程ID',
  `content` longtext CHARACTER SET utf8mb4 COMMENT '工单内容',
  `workspace_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工作空间ID',
  `status` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单状态',
  PRIMARY KEY (`id`),
  KEY `IDX_CREATOR` (`creator`) USING BTREE,
  KEY `IDX_PROCESS` (`process_id`),
  KEY `IDX_MODEL` (`model_id`),
  KEY `IDX_WORKSPACE_ID` (`workspace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='工单模板';

-- ----------------------------
--  Table structure for `ticket_config`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket_config` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '工单模板ID',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单模板名称',
  `description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单模板描述',
  `form_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '表单ID',
  `model_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '流程模型ID',
  `time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IDX_MODEL` (`model_id`),
  KEY `IDX_FORM` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `ticket_form`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket_form` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '表单ID',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '表单名称',
  `content` longtext CHARACTER SET utf8mb4 NOT NULL COMMENT '表单内容',
  `version` bigint(13) NOT NULL COMMENT '版本',
  `creator` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建者',
  `deploy_id` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '部署ID',
  PRIMARY KEY (`id`),
  KEY `IDX_DEPLOY_ID` (`deploy_id`) USING BTREE,
  KEY `IDX_VERSION` (`version`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `ticket_form_deploy`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket_form_deploy` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '部署ID',
  `form_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '表单ID',
  `content` longtext CHARACTER SET utf8mb4 COMMENT '部署表单内容',
  `version` bigint(13) NOT NULL COMMENT '部署版本',
  `time` bigint(13) DEFAULT NULL COMMENT '部署时间',
  PRIMARY KEY (`id`),
  KEY `IDX_FORM_ID` (`form_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `ticket_tag`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket_tag` (
  `tag_key` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '标签KEY',
  `tag_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标签值',
  `_index` int(10) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`tag_key`),
  UNIQUE KEY `IDX_TAG_NAME` (`tag_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `ticket_tag_mapping`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ticket_tag_mapping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '标签映射ID',
  `tag_key` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标签KEY',
  `ticket_config_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单模板ID',
  PRIMARY KEY (`id`),
  KEY `IDX_TAG_KEY` (`tag_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;