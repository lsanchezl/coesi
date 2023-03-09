package com.coesi.service;

import com.coesi.domain.Authority;
import com.coesi.domain.Teacher;
import com.coesi.domain.User;
import com.coesi.repository.AuthorityRepository;
import com.coesi.repository.TeacherRepository;
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
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a teacher.
     *
     * @param teacher the entity to save.
     * @return the persisted entity.
     */
    public Teacher save(Teacher teacher) {
        log.debug("Request to save Teacher : {}", teacher);

        Authority autTeacher = authorityRepository.findById(AuthoritiesConstants.TEACHER).get();
        User teacherUser = userRepository.findById(teacher.getUser().getId()).get();
        if (!teacherUser.getAuthorities().contains(autTeacher)) {
            teacherUser.getAuthorities().add(autTeacher);
            userRepository.save(teacherUser);
        }

        return teacherRepository.save(teacher);
    }

    /**
     * Get all the teachers.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Teacher> findAll(UserDTO user, Pageable pageable) {
        if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)) {
            log.debug("Request to get all Students for PRINCIPAL");
            return teacherRepository.findAll(pageable);
        } else {
            log.debug("@currentAuthority not allowed;");
            return null;
        }
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Teacher> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findById(id);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);

        Teacher currentTeacher = teacherRepository.findById(id).get();

        Authority autTeacher = authorityRepository.findById(AuthoritiesConstants.TEACHER).get();
        User teacherUser = userRepository.findById(currentTeacher.getUser().getId()).get();
        teacherUser.getAuthorities().remove(autTeacher);
        userRepository.save(teacherUser);

        teacherRepository.deleteById(id);
    }

    public List<Teacher> getAllTeachersList() {
        return teacherRepository.findAll();
    }
}
