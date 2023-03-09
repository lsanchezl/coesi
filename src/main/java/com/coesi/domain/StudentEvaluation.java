package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StudentEvaluation.
 */
@Entity
@Table(name = "student_evaluation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "score", nullable = false)
    private Double score;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "studentEvaluations", allowSetters = true)
    private GroupStudent groupStudent;

    @ManyToOne
    @JsonIgnoreProperties(value = "studentEvaluations", allowSetters = true)
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "studentEvaluations", allowSetters = true)
    private EvaluationCriteria evaluationCriteria;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public StudentEvaluation score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public GroupStudent getGroupStudent() {
        return groupStudent;
    }

    public StudentEvaluation groupStudent(GroupStudent groupStudent) {
        this.groupStudent = groupStudent;
        return this;
    }

    public void setGroupStudent(GroupStudent groupStudent) {
        this.groupStudent = groupStudent;
    }

    public Status getStatus() {
        return status;
    }

    public StudentEvaluation status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EvaluationCriteria getEvaluationCriteria() {
        return evaluationCriteria;
    }

    public StudentEvaluation evaluationCriteria(EvaluationCriteria evaluationCriteria) {
        this.evaluationCriteria = evaluationCriteria;
        return this;
    }

    public void setEvaluationCriteria(EvaluationCriteria evaluationCriteria) {
        this.evaluationCriteria = evaluationCriteria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentEvaluation)) {
            return false;
        }
        return id != null && id.equals(((StudentEvaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentEvaluation{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
