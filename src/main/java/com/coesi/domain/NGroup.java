package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A NGroup.
 */
@Entity
@Table(name = "n_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private SchoolCycle schoolCycle;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private Teacher teacher;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private NClass nClass;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private Room room;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private Modality modality;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nGroups", allowSetters = true)
    private StatusGroup statusGroup;

    @Transient
    @JsonSerialize
    private StatusGroup previousStatusGroup;

    @Transient
    @JsonSerialize
    private StatusGroup nextStatusGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public NGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public NGroup startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public NGroup endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SchoolCycle getSchoolCycle() {
        return schoolCycle;
    }

    public NGroup schoolCycle(SchoolCycle schoolCycle) {
        this.schoolCycle = schoolCycle;
        return this;
    }

    public void setSchoolCycle(SchoolCycle schoolCycle) {
        this.schoolCycle = schoolCycle;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public NGroup teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public NClass getNClass() {
        return nClass;
    }

    public NGroup nClass(NClass nClass) {
        this.nClass = nClass;
        return this;
    }

    @JsonProperty("nClass")
    public void setNClass(NClass nClass) {
        this.nClass = nClass;
    }

    public Room getRoom() {
        return room;
    }

    public NGroup room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Modality getModality() {
        return modality;
    }

    public NGroup modality(Modality modality) {
        this.modality = modality;
        return this;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public StatusGroup getStatusGroup() {
        return statusGroup;
    }

    public NGroup statusGroup(StatusGroup statusGroup) {
        this.statusGroup = statusGroup;
        return this;
    }

    public void setStatusGroup(StatusGroup statusGroup) {
        this.statusGroup = statusGroup;
    }

    public StatusGroup getPreviousStatusGroup() {
        return previousStatusGroup;
    }

    public void setPreviousStatusGroup(StatusGroup previousStatusGroup) {
        this.previousStatusGroup = previousStatusGroup;
    }

    public StatusGroup getNextStatusGroup() {
        return nextStatusGroup;
    }

    public void setNextStatusGroup(StatusGroup nextStatusGroup) {
        this.nextStatusGroup = nextStatusGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NGroup)) {
            return false;
        }
        return id != null && id.equals(((NGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NGroup{"
                + "id=" + getId()
                + ", name='" + getName() + "'"
                + ", startDate='" + getStartDate() + "'"
                + ", endDate='" + getEndDate() + "'"
                + ", statusGroup='" + (getStatusGroup() == null ? "null" : getStatusGroup().getKeyStatus()) + "'"
                + "}";
    }
}
