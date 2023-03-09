TRUNCATE TABLE student_evaluation;

ALTER TABLE `student_evaluation` ADD UNIQUE INDEX `student_evaluacion_unique` (`group_student_id`, `evaluation_criteria_id` );