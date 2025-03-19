﻿CREATE TABLE `schedule`
(
    `schedule_id`  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title`        VARCHAR(255)  NOT NULL,
    `content`      VARCHAR(2000) NOT NULL,
    `create_date`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_name`    VARCHAR(100)  NOT NULL,
    `password`     VARCHAR(255)  NOT NULL
);
