package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GroupStudent.
 */
@Entity
@Table(name = "group_student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opportunity1")
    private Double opportunity1;

    @Column(name = "opportunity2")
    private Double opportunity2;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "groupStudents", allowSetters = true)
    private Student student;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "groupStudents", allowSetters = true)
    private NGroup nGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOpportunity1() {
        return opportunity1;
    }

    public void setOpportunity1(Double opportunity1) {
        this.opportunity1 = opportunity1;
    }

    public Double getOpportunity2() {
        return opportunity2;
    }

    public void setOpportunity2(Double opportunity2) {
        this.opportunity2 = opportunity2;
    }

    public Student getStudent() {
        return student;
    }

    public GroupStudent student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public NGroup getNGroup() {
        return nGroup;
    }

    public GroupStudent nGroup(NGroup nGroup) {
        this.nGroup = nGroup;
        return this;
    }

    @JsonProperty("nGroup")
    public void setNGroup(NGroup nGroup) {
        this.nGroup = nGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupStudent)) {
            return false;
        }
        return id != null && id.equals(((GroupStudent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupStudent{"
                + "id=" + getId()
                + "}";
    }
}
