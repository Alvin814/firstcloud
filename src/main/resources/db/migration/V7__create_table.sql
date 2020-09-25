CREATE TABLE IF NOT EXISTS `flow_form_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '消息配置ID',
  `model_id` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程模型ID',
  `form_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '表单ID',
  `step` int(10) DEFAULT NULL COMMENT '环节顺序号',
  `process_type` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程类型',
  `position` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '触发时机',
  `operation` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '触发操作',
  `module` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '模块ID',
  PRIMARY KEY (`id`),
  KEY `IDX_MODEL_ID` (`model_id`) USING BTREE,
  KEY `IDX_STEP` (`step`) USING BTREE,
  KEY `IDX_MODULE` (`module`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;