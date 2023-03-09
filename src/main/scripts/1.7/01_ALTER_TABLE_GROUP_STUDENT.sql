ALTER TABLE `naucara_coesi_prd`.`group_student` 
ADD COLUMN `opportunity1` DOUBLE AFTER `n_group_id`,
ADD COLUMN `opportunity2` DOUBLE AFTER `opportunity1`;