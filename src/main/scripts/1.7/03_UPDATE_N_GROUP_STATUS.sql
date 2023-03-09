UPDATE n_group 
SET 
    status_group_id = (SELECT 
            id
        FROM
            status_group
        WHERE
            key_status = 'SENT')
WHERE
    (end_date <= STR_TO_DATE('2022,03,01', '%Y,%m,%d %h,%i,%s')
        OR start_date IS NULL ) and id > 0;

UPDATE n_group 
SET 
    status_group_id = (SELECT 
            id
        FROM
            status_group
        WHERE
            key_status = 'IN_PROGRESS')
WHERE
    end_date > STR_TO_DATE('2022,03,01', '%Y,%m,%d %h,%i,%s')
        AND id > 0;