CREATE TABLE ticket_watch_list(
id INT (11) PRIMARY KEY AUTO_INCREMENT,
day_user_id VARCHAR (255),
day_user_name VARCHAR (255),
night_user_id VARCHAR (255),
night_user_name VARCHAR (255),
watch_date DATE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;