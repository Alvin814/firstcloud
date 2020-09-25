CREATE TABLE IF NOT EXISTS `gh_cost` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT 'ID',
  `ticket_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工单ID',
  `user_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '填写人ID',
  `workspace_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工作空间ID',
  `organization_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '组织ID',
  `cost`  decimal(12, 4)       DEFAULT NULL COMMENT  '工时',
  `time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IDX_WORKSPACE_ID` (`workspace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='工单模板';