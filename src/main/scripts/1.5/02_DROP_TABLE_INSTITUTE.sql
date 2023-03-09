ALTER TABLE `career`
  DROP FOREIGN KEY `fk_career_institute_id`;

ALTER TABLE `career`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_career_institute_id`;

ALTER TABLE `generation`
  DROP FOREIGN KEY `fk_generation_institute_id`;

ALTER TABLE `generation`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_generation_institute_id`;

ALTER TABLE `performance_indicator`
  DROP FOREIGN KEY `fk_performance_indicator_institute_id`;

ALTER TABLE `performance_indicator`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_performance_indicator_institute_id`;

ALTER TABLE `room`
  DROP FOREIGN KEY `fk_room_institute_id`;

ALTER TABLE `room`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_room_institute_id`;

ALTER TABLE `school_cycle`
  DROP FOREIGN KEY `fk_school_cycle_institute_id`;

ALTER TABLE `school_cycle`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_school_cycle_institute_id_idx`;

ALTER TABLE `student`
  DROP FOREIGN KEY `fk_student_institute_id`;

ALTER TABLE `student`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_student_institute_id`;

ALTER TABLE `teacher`
  DROP FOREIGN KEY `fk_teacher_institute_id`;

ALTER TABLE `teacher`
  DROP COLUMN `institute_id`,
  DROP INDEX `fk_teacher_institute_id`;

DROP TABLE institute; 