UPDATE group_student gs 
SET 
    opportunity1 = (SELECT 
            ROUND(SUM(((`se`.`score` * `ec`.`percentage`) / 100)),
                        0) op1
        FROM
            student_evaluation se,
            evaluation_criteria ec
        WHERE
            se.evaluation_criteria_id = ec.id
                AND se.group_student_id = gs.id)
WHERE
    gs.id > 1;