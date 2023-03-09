ALTER TABLE `coesi`.`institute` 
ADD COLUMN `created_by_id` BIGINT NULL AFTER `treasurer_id`,
ADD COLUMN `created_date` TIMESTAMP NULL AFTER `created_by_id`,
ADD INDEX `fk_institute_created_by_id_idx` (`created_by_id` ASC) VISIBLE;
;
ALTER TABLE `coesi`.`institute` 
ADD CONSTRAINT `fk_institute_created_by_id`
  FOREIGN KEY (`created_by_id`)
  REFERENCES `coesi`.`jhi_user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
update institute set created_by_id = 5, created_date = now() where id = 27;

ALTER TABLE `coesi`.`institute` 
DROP FOREIGN KEY `fk_institute_created_by_id`;

ALTER TABLE `coesi`.`institute` 
CHANGE COLUMN `created_by_id` `created_by_id` BIGINT NOT NULL ,
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ;

ALTER TABLE `coesi`.`institute` 
ADD CONSTRAINT `fk_institute_created_by_id`
  FOREIGN KEY (`created_by_id`)
  REFERENCES `coesi`.`jhi_user` (`id`);