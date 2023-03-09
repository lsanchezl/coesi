CREATE TABLE `status_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `key_status` varchar(20) NOT NULL,
  `order_status` bigint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

INSERT INTO `status_group` VALUES (15,'Creado','CREATED',1),(16,'En curso','IN_PROGRESS',2),(17,'Enviado','SENT',3),(18,'Cerrado','CLOSED',4);