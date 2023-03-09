package com.coesi.service;

import com.coesi.domain.EvaluationCriteria;
import com.coesi.domain.GroupStudent;
import com.coesi.domain.StudentEvaluation;
import com.coesi.repository.EvaluationCriteriaRepository;
import com.coesi.repository.GroupStudentRepository;
import com.coesi.repository.StudentEvaluationRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Service Implementation for managing {@link StudentEvaluation}.
 */
@Service
@Transactional
public class StudentEvaluationService {

    @PersistenceContext
    private EntityManager entityManager;

    private final Logger log = LoggerFactory.getLogger(StudentEvaluationService.class);

    private final StudentEvaluationRepository studentEvaluationRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final GroupStudentRepository groupStudentRepository;

    public StudentEvaluationService(StudentEvaluationRepository studentEvaluationRepository,
            EvaluationCriteriaRepository evaluationCriteriaRepository,
            GroupStudentRepository groupStudentRepository) {
        this.studentEvaluationRepository = studentEvaluationRepository;
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
        this.groupStudentRepository = groupStudentRepository;
    }

    /**
     * Save a studentEvaluation.
     *
     * @param studentEvaluation the entity to save.
     * @return the persisted entity.
     */
    public StudentEvaluation save(StudentEvaluation studentEvaluation) {
        log.debug("Request to save StudentEvaluation : {}", studentEvaluation);
        return studentEvaluationRepository.save(studentEvaluation);
    }

    /**
     * Get all the studentEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentEvaluation> findAll(Pageable pageable) {
        log.debug("Request to get all StudentEvaluations");
        return studentEvaluationRepository.findAll(pageable);
    }

    /**
     * Get one studentEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentEvaluation> findOne(Long id) {
        log.debug("Request to get StudentEvaluation : {}", id);
        return studentEvaluationRepository.findById(id);
    }

    /**
     * Delete the studentEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentEvaluation : {}", id);
        studentEvaluationRepository.deleteById(id);
    }

    /**
     * Regresa todas las evaluaciones del grupo, en forma de matriz, con los
     * criterios de evaluación como encabezado
     *
     * @param nGroupId
     * @param login
     * @return
     */
    public Optional<String[][]> findByGroupMatrix(Long nGroupId, String login) {
        List<EvaluationCriteria> evaluationCriterias = evaluationCriteriaRepository.findAllByNGroup(nGroupId);
        StringBuilder sqlQuery = new StringBuilder("SELECT  gs.id, CONCAT(u.last_name, ' ', u.first_name) full_name,");

        if (!evaluationCriterias.isEmpty()) {
            int i;
            for (i = 0; i < evaluationCriterias.size(); i++) {
                sqlQuery.append(" IFNULL((select score from student_evaluation se where evaluation_criteria_id = ");
                sqlQuery.append(evaluationCriterias.get(i).getId());
                sqlQuery.append(" and se.group_student_id = gs.id ), 0) \"");
                sqlQuery.append(evaluationCriterias.get(i).getId());
                sqlQuery.append("\",");
            }
        }
        sqlQuery.append(" IFNULL(gs.opportunity1, 0) opportunity1");
        sqlQuery.append(" FROM");
        sqlQuery.append(" n_group g,");
        sqlQuery.append(" group_student gs,");
        sqlQuery.append(" student s,");
        sqlQuery.append(" jhi_user u");
        sqlQuery.append(" WHERE");
        sqlQuery.append(" g.id = ");
        sqlQuery.append(nGroupId);
        sqlQuery.append(" AND g.id = gs.n_group_id");
        sqlQuery.append(" AND gs.student_id = s.id");
        sqlQuery.append(" AND s.user_id = u.id");
        if (login != null) {
            sqlQuery.append(" AND u.login = '");
            sqlQuery.append(login);
            sqlQuery.append("'");
        }
        sqlQuery.append(" ORDER BY u.last_name , u.first_name");

        Query query = entityManager.createNativeQuery(sqlQuery.toString());
        List<Object[]> evaluations = query.getResultList();

        int rowLength = evaluations.get(0).length;
        int rowIndex = 0;
        int cellIndex = 0;
        // row 1 lleva los ids de los critrerios, row 2 las ponderaciones y row 3 los nombres de los criterios
        String[][] result = new String[evaluations.size() + 3][rowLength];

        String[] percentajes = new String[rowLength];
        String[] headers = new String[rowLength];
        String[] evaluationCriteriasId = new String[rowLength];

        evaluationCriteriasId[cellIndex] = "0";
        percentajes[cellIndex] = "0";
        headers[cellIndex++] = "groupStudentId";

        evaluationCriteriasId[cellIndex] = "0";
        percentajes[cellIndex] = "0";
        headers[cellIndex++] = "Nombre";

        for (EvaluationCriteria evaluationCriteria : evaluationCriterias) {
            evaluationCriteriasId[cellIndex] = evaluationCriteria.getId().toString();
            percentajes[cellIndex] = evaluationCriteria.getPercentage().toString();
            headers[cellIndex++] = evaluationCriteria.getPercentage() + " "
                    + evaluationCriteria.getPerformanceIndicator().getName() + ": "
                    + evaluationCriteria.getDescription();
        }
        evaluationCriteriasId[cellIndex] = "0";
        percentajes[cellIndex] = "0";
        headers[cellIndex++] = "Total";

        result[rowIndex++] = evaluationCriteriasId;
        result[rowIndex++] = percentajes;
        result[rowIndex++] = headers;

        int i;
        String[] studentScore;

        //recorremos el resultado del query de evaluaciones
        for (Object[] evaluation : evaluations) {
            cellIndex = 0;
            studentScore = new String[rowLength];

            // las primeras dos columnas son el id y el nombre
            studentScore[cellIndex++] = evaluation[0].toString();
            studentScore[cellIndex++] = evaluation[1].toString();

            for (i = 2; i < evaluation.length - 1; i++) {
                // la calificación viene en decimal, lo convertimos a entero para quitarle el .0
                studentScore[cellIndex++] = (new Double(evaluation[i].toString())).intValue() + "";
            }
            studentScore[cellIndex] = (new Double(evaluation[i].toString())).intValue() + "";
            result[rowIndex++] = studentScore;
        }

        return Optional.of(result);
    }

    private StudentEvaluation searchEvaluation(List<StudentEvaluation> evaluationsDB, long groupStudentId, long evaluationCriteriaId) {
        for (int i = 0; i < evaluationsDB.size(); i++) {
            if ((evaluationsDB.get(i).getGroupStudent().getId() == groupStudentId)
                    && (evaluationsDB.get(i).getEvaluationCriteria().getId() == evaluationCriteriaId)) {
                // eliminamos el elemento para agilizar la siguiente búsqueda
                return evaluationsDB.remove(i);
            }
        }
        return null;
    }

    public void update(String[][] matrix) throws Exception {
        List<StudentEvaluation> evaluationsToUpdate = new ArrayList<>();
        List<GroupStudent> groupStudentsToUpdate = new ArrayList<>();
        EvaluationCriteria[] evalutionCriterias = new EvaluationCriteria[matrix[0].length];
        GroupStudent[] groupStudents = new GroupStudent[matrix.length];
        EvaluationCriteria evaluationCriteria;
        GroupStudent groupStudent;

        //buscamos las evaluaciones del grupo
        //el grupo lo obtenemos del primer alumno que venga en la matrix
        List<StudentEvaluation> evaluationsDB = studentEvaluationRepository.findAllEvaluationsGroups(Long.parseLong(matrix[3][0]));

        StudentEvaluation studentEvaluation = null;
        Double score;
        Double opportunity1;
        long groupStudentId;
        long evaluationCriteriaId;
        int columnIndex;
        // registro 0: ids de los criterios de evaluación
        // registro 1: porcentaje de calificación ponderada de cada criterio
        // registro 2: descripción del criterio de evaluación
        for (int rowIndex = 3; rowIndex < matrix.length; rowIndex++) {
            // columna 1: groupStudentId
            // columna 2: nombre del alumno
            // penúltima columna: oportunidad
            // última columna: total
            for (columnIndex = 2; columnIndex < matrix[rowIndex].length - 1; columnIndex++) {

                groupStudentId = Long.parseLong(matrix[rowIndex][0]);
                evaluationCriteriaId = Long.parseLong(matrix[0][columnIndex]);
                studentEvaluation = searchEvaluation(evaluationsDB, groupStudentId, evaluationCriteriaId);

                score = Double.parseDouble(matrix[rowIndex][columnIndex]);

                // si encontramos una evaluación, vamos a actualizar solo si la calificación fue modificada
                if (studentEvaluation != null) {
                    if (!studentEvaluation.getScore().equals(score)) {
                        studentEvaluation.setScore(score);
                        evaluationsToUpdate.add(studentEvaluation);
                    }
                } else {
                    // si no encontró la evaluación, significa que es la primera vez que va a calificar a ese alumno en ese criterio
                    studentEvaluation = new StudentEvaluation();

                    // revisamos si ya existe ese criterio de evaluación en nuestro arreglo, y si no, lo buscamos y guardamos
                    evaluationCriteria = evalutionCriterias[columnIndex];
                    if (evaluationCriteria == null) {
                        evaluationCriteria = evaluationCriteriaRepository.getOne(evaluationCriteriaId);
                        evalutionCriterias[columnIndex] = evaluationCriteria;
                    }
                    studentEvaluation.setEvaluationCriteria(evaluationCriteria);

                    // revisamos si ya existe ese alumno en nuestro arreglo, y si no, lo buscamos y guardamos
                    groupStudent = groupStudents[rowIndex];
                    if (groupStudent == null) {
                        groupStudent = groupStudentRepository.getOne(groupStudentId);
                        groupStudents[rowIndex] = groupStudent;
                    }
                    studentEvaluation.setGroupStudent(groupStudent);
                    studentEvaluation.setScore(score);
                    evaluationsToUpdate.add(studentEvaluation);
                }
            }
            //la oportunidad y calificación total se guarda en groupStudent
            //lo obtenemos de studenEvaluation porque estamos seguro de que es el que esta persistido
            if (studentEvaluation != null) {
                groupStudent = studentEvaluation.getGroupStudent();

                opportunity1 = Double.parseDouble(matrix[rowIndex][columnIndex]);

                //si la oportunidad o el total son diferentes a los que están en base de datos, los persistimos
                if (!opportunity1.equals(groupStudent.getOpportunity1())) {
                    groupStudent.setOpportunity1(opportunity1);
                    groupStudentsToUpdate.add(groupStudent);
                }
            }
        }
        studentEvaluationRepository.saveAll(evaluationsToUpdate);
        groupStudentRepository.saveAll(groupStudentsToUpdate);
    }

}
