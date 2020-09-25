ALTER TABLE gh_cost add `sync_year` varchar(4)           DEFAULT NULL COMMENT '年';
ALTER TABLE gh_cost add `sync_month`          varchar(7)           DEFAULT NULL COMMENT '月';
ALTER TABLE gh_cost add `sync_week`           varchar(7)           DEFAULT NULL COMMENT '周';
ALTER TABLE gh_cost add `sync_day`            varchar(10)          DEFAULT NULL COMMENT '日';
ALTER TABLE gh_cost add `sync_hour`           varchar(13) NOT NULL DEFAULT '' COMMENT '时';