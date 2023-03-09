package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendance implements Serializable, Comparable<Attendance> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_attendance", nullable = false)
    private LocalDate dateAttendance;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private TypeAttendance typeAttendance;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private GroupStudent groupStudent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAttendance() {
        return dateAttendance;
    }

    public Attendance dateAttendance(LocalDate dateAttendance) {
        this.dateAttendance = dateAttendance;
        return this;
    }

    public void setDateAttendance(LocalDate dateAttendance) {
        this.dateAttendance = dateAttendance;
    }

    public TypeAttendance getTypeAttendance() {
        return typeAttendance;
    }

    public Attendance typeAttendance(TypeAttendance typeAttendance) {
        this.typeAttendance = typeAttendance;
        return this;
    }

    public void setTypeAttendance(TypeAttendance typeAttendance) {
        this.typeAttendance = typeAttendance;
    }

    public GroupStudent getGroupStudent() {
        return groupStudent;
    }

    public Attendance groupStudent(GroupStudent groupStudent) {
        this.groupStudent = groupStudent;
        return this;
    }

    public void setGroupStudent(GroupStudent groupStudent) {
        this.groupStudent = groupStudent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{"
                + "id=" + getId()
                + "}";
    }

    @Override
    public int compareTo(Attendance otherAttendance) {
        int cmp = (getDateAttendance().getYear() - otherAttendance.getDateAttendance().getYear());
        if (cmp == 0) {
            cmp = (getDateAttendance().getDayOfYear() - otherAttendance.getDateAttendance().getDayOfYear());
        }
        return cmp;
    }
}
