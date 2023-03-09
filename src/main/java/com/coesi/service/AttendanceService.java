package com.coesi.service;

import com.coesi.domain.Attendance;
import com.coesi.domain.GroupStudent;
import com.coesi.domain.TypeAttendance;
import com.coesi.model.GeneralParam;
import com.coesi.model.AttendanceMatrix;
import com.coesi.repository.AttendanceRepository;
import com.coesi.repository.GroupStudentRepository;
import com.coesi.repository.TypeAttendanceRepository;
import com.coesi.service.dto.UserDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Service Implementation for managing {@link Attendance}.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final TypeAttendanceRepository typeAttendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
            GroupStudentRepository groupStudentRepository, TypeAttendanceRepository typeAttendanceRepository) {
        this.attendanceRepository = attendanceRepository;
        this.groupStudentRepository = groupStudentRepository;
        this.typeAttendanceRepository = typeAttendanceRepository;
    }

    /**
     * Save a attendance.
     *
     * @param attendance the entity to save.
     * @return the persisted entity.
     */
    public Attendance save(Attendance attendance) {
        log.debug("Request to save Attendance : {}", attendance);
        return attendanceRepository.save(attendance);
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Attendance> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll(pageable);
    }

    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Attendance> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
    }

    /**
     * Recibe una fecha y un grupo, e inserta las asistencias para todos los
     * estudiantes de ese grupo en esa fecha.
     *
     * @param attendanceGroup
     * @return
     */
    public List<Attendance> createByGroup(GeneralParam attendanceGroup) {
        List<Attendance> result = new ArrayList<>();
        List<GroupStudent> groupStudents = groupStudentRepository.findAllByNGroupId(attendanceGroup.getId());
        TypeAttendance typeAttendance = typeAttendanceRepository.findAll().get(0);
        Attendance attendance;
        for (GroupStudent groupStudent : groupStudents) {
            attendance = new Attendance();
            attendance.setDateAttendance(attendanceGroup.getValueLocalDate());
            attendance.setGroupStudent(groupStudent);
            attendance.setTypeAttendance(typeAttendance);
            attendanceRepository.save(attendance);
            result.add(attendance);
        }
        return result;
    }

    /**
     * Regresa todas las asistencia del grupo, en forma de matriz, con las
     * fechas como encabezado
     *
     * @param nGroupId
     * @return
     */
    public Optional<AttendanceMatrix> findByGroupMatrix(Long nGroupId) {
        // Consultamos las asistencias que hay en base de datos
        List<Attendance> attendancesDB = attendanceRepository.findAllByNGroup(nGroupId);

        // Consultamos los alumnos inscritos al grupo
        List<GroupStudent> groupStudents = groupStudentRepository.findAllByNGroupId(nGroupId);

        return findMatrix(attendancesDB, groupStudents);
    }

    /**
     * Regresa todas las asistencia del grupo para el alumno indicado, en forma
     * de matriz, con las fechas como encabezado
     *
     * @param user
     * @param nGroupId
     * @return
     */
    public Optional<AttendanceMatrix> findByGroupAndLoginMatrix(UserDTO user, Long nGroupId) {
        // Consultamos las asistencias que hay en base de datos
        List<Attendance> attendancesDB = attendanceRepository.findByNGroupIdAndLogin(nGroupId, user.getLogin());

        // Consultamos los alumnos inscritos al grupo
        GroupStudent groupStudent = groupStudentRepository.findByNGroupIdAndLogin(nGroupId, user.getLogin());
        List<GroupStudent> groupStudents = new ArrayList<>();
        groupStudents.add(groupStudent);

        return findMatrix(attendancesDB, groupStudents);
    }

    public Optional<AttendanceMatrix> findMatrix(List<Attendance> attendancesDB, List<GroupStudent> groupStudents) {
        // La llave es el id del groupStudent y el valor la lista de asistencias de ese estudiante
        SortedMap<Long, List<Attendance>> mapAttendances = new TreeMap<>();

        // Lista de un distinct de las fechas en la lista de asistencias
        List<LocalDate> datesAttendances = new ArrayList<>();

        // Lista de asistencias que se han agregado en el map para un estudiante
        List<Attendance> tempAttendances;

        // Bandera para saber si ya se agregó o no la fecha
        boolean exist;

        // Llave del mapa
        Long studentId;

        // Recorremos todas las asistencias para organizarlas, cada fecha será una columna en la tabla final
        for (Attendance attendance : attendancesDB) {

            // En caso de encontrar una nueva fecha, se agrega a la lista
            if (!datesAttendances.contains(attendance.getDateAttendance())) {
                datesAttendances.add(attendance.getDateAttendance());
            }

            // Obtenemos la llave para el mapa
            studentId = attendance.getGroupStudent().getId();

            // Revisamos si existe el elemento
            if (mapAttendances.containsKey(studentId)) {

                //cuando ya existe el elemento, agregamos la nueva fecha si aún no la tiene
                tempAttendances = mapAttendances.get(studentId);
                exist = false;
                for (Attendance attendanceAlreadyAdded : tempAttendances) {
                    if (attendance.getDateAttendance().equals(attendanceAlreadyAdded.getDateAttendance())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    tempAttendances.add(attendance);
                }
                mapAttendances.put(studentId, tempAttendances);
            } else {
                // primera vez que se agrega el elemento
                tempAttendances = new ArrayList<>();
                tempAttendances.add(attendance);
                mapAttendances.put(studentId, tempAttendances);
            }
        }

        // Lista de asistencias que se han agregado para un estudiante
        List<Attendance> studentAttendances;

        // Matriz final que se regresará como resultado
        AttendanceMatrix matrix = new AttendanceMatrix();

        matrix.addHeader("Att");
        matrix.addHeader("Nombre");

        // Ordenamos las fechas y las agregamos como encabezados a la matriz
        Collections.sort(datesAttendances);
        datesAttendances.forEach((dateAttendance) -> {
            matrix.addHeader(dateAttendance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        // Definimos un tipo de asistencia default, la falta
        TypeAttendance absence = typeAttendanceRepository.findByName("Falta");

        // Se usa para crear una nueva asistencia
        Attendance attendance;

        // Nombre del tipo de asistencia del estudiante
        List<String> data;

        // Ids de las asistencias del estudiante
        List<Long> ids;

        // Recorremos la lista de todos los estudiantes para ordenar y agregar (en caso de que haga falta) sus asistencias
        for (GroupStudent groupStudent : groupStudents) {

            // lista de las asistencias del estudiante
            studentAttendances = mapAttendances.get(groupStudent.getId());

            // si es nulo significa que encontró un estudiante inscrito al grupo pero que aún no tiene asistencias
            if (studentAttendances == null) {
                studentAttendances = new ArrayList<>();
                mapAttendances.put(groupStudent.getId(), studentAttendances);
            }

            // Si la persona no tiene alguna fecha, se agrega como falta
            for (LocalDate dateAttendance : datesAttendances) {
                exist = false;
                for (Attendance attStudent : studentAttendances) {
                    if (attStudent.getDateAttendance().equals(dateAttendance)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    // Si no tiene una asistencia para la fecha, se crea y se agrega
                    attendance = new Attendance();
                    attendance.setDateAttendance(dateAttendance);
                    attendance.setGroupStudent(groupStudent);
                    attendance.setTypeAttendance(absence);
                    attendanceRepository.save(attendance);
                    studentAttendances.add(attendance);
                }
            }

            // Ordenamos las asistencias por fecha
            Collections.sort(studentAttendances);

            // Inicializamos los arreglos de datos
            data = new ArrayList<>();
            ids = new ArrayList<>();

            // Agregamos el nGroupId y el nombre
            data.add(groupStudent.getId().toString());
            data.add(groupStudent.getStudent().getUser().getFullName());

            // Las primeras dos columnas son sni valor
            ids.add(0L);
            ids.add(0L);

            // Agregamos el tipo de asistencia y id de la asistencia
            for (Attendance studentAttendance : studentAttendances) {
                data.add(studentAttendance.getTypeAttendance().getName());
                ids.add(studentAttendance.getId());
            }
            matrix.addData(data);
            matrix.addIds(ids);
        }
        return Optional.of(matrix);
    }

    /**
     * En el id viene el attendance_id y en el value el tipo de asistencia.
     *
     * @param generalParam
     * @return
     */
    public Attendance update(GeneralParam generalParam) {
        Attendance attendance = attendanceRepository.findById(generalParam.getId()).get();

        TypeAttendance typeAttendance = typeAttendanceRepository.findById(generalParam.getLong()).get();
        attendance.setTypeAttendance(typeAttendance);

        save(attendance);
        return attendance;
    }
}
