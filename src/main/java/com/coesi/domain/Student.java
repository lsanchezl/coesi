package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tuition", length = 100, nullable = false)
    private String tuition;

    @Column(name = "graduation_sermon")
    private Integer graduationSermon;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Generation generation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private Career career;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTuition() {
        return tuition;
    }

    public Student tuition(String tuition) {
        this.tuition = tuition;
        return this;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public Integer getGraduationSermon() {
        return graduationSermon;
    }

    public Student graduationSermon(Integer graduationSermon) {
        this.graduationSermon = graduationSermon;
        return this;
    }

    public void setGraduationSermon(Integer graduationSermon) {
        this.graduationSermon = graduationSermon;
    }

    public User getUser() {
        return user;
    }

    public Student user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Generation getGeneration() {
        return generation;
    }

    public Student generation(Generation generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public Career getCareer() {
        return career;
    }

    public Student career(Career career) {
        this.career = career;
        return this;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{"
                + "id=" + getId()
                + ", tuition='" + getTuition() + "'"
                + ", graduationSermon=" + getGraduationSermon()
                + "}";
    }
}
