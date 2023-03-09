ALTER TABLE `n_group` 
ADD COLUMN `status_group_id` BIGINT NULL AFTER `school_cycle_id`;

UPDATE n_group SET status_group_id = 15 WHERE id > 1;

ALTER TABLE `n_group` 
CHANGE COLUMN `status_group_id` `status_group_id` BIGINT NOT NULL ;
