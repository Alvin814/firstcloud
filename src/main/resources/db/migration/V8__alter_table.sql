ALTER TABLE `flow_form_config` ADD COLUMN `activity_id` varchar(36) COMMENT '环节ID' AFTER `model_id`, ADD INDEX `IDX_ACTIVITY_ID` (`activity_id`) comment '';

ALTER TABLE `ticket_config` MODIFY `form_id` varchar(64) CHARACTER SET utf8mb4  NULL;