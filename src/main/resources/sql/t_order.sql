CREATE TABLE `t_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT NULL,
  `order_createtime` DATETIME DEFAULT NULL,
  `order_state` INT(1) DEFAULT NULL,
  `order_money` DOUBLE DEFAULT NULL,
  `commodity_id` INT(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci