ALTER TABLE ticket add `handle_program` VARCHAR(50) NULL COMMENT '处理方案',
add `handle_result` VARCHAR(50) NULL COMMENT '处理结果',
add `ticket_handle_result` VARCHAR(50) NULL COMMENT '处理结果确认',
add `handle_speed` INT(11) NULL COMMENT '处理速度',
add `handle_attitude` INT(11) NULL COMMENT '处理态度',
add `handle_satisfaction` INT(11) NULL COMMENT '处理满意度';
