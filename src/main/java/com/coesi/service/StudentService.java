package com.coesi.service;

import com.coesi.domain.Authority;
import com.coesi.domain.Student;
import com.coesi.domain.User;
import com.coesi.repository.AuthorityRepository;
import com.coesi.repository.StudentRepository;
import com.coesi.repository.UserRepository;
import com.coesi.security.AuthoritiesConstants;
import com.coesi.service.dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);

        Authority autStudent = authorityRepository.findById(AuthoritiesConstants.STUDENT).get();
        User studentUser = userRepository.findById(student.getUser().getId()).get();
        if (!studentUser.getAuthorities().contains(autStudent)) {
            studentUser.getAuthorities().add(autStudent);
            userRepository.save(studentUser);
        }

        return studentRepository.save(student);
    }

    /**
     * Get all the students.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findAll(UserDTO user, Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);

        Student currentStudent = studentRepository.findById(id).get();

        Authority autStudent = authorityRepository.findById(AuthoritiesConstants.STUDENT).get();
        User studentUser = userRepository.findById(currentStudent.getUser().getId()).get();
        studentUser.getAuthorities().remove(autStudent);
        userRepository.save(studentUser);

        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudentsList() {
        return studentRepository.findAll();
    }
}
