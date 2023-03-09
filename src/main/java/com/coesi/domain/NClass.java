package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A NClass.
 */
@Entity
@Table(name = "n_class")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "key_class", length = 100)
    private String keyClass;

    @Size(max = 100)
    @Column(name = "area", length = 100)
    private String area;

    @Size(max = 100)
    @Column(name = "division", length = 100)
    private String division;

    @Column(name = "units")
    private Double units;

    @Column(name = "n_order")
    private Integer nOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "nClasses", allowSetters = true)
    private Career career;

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

    public NClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyClass() {
        return keyClass;
    }

    public NClass keyClass(String keyClass) {
        this.keyClass = keyClass;
        return this;
    }

    public void setKeyClass(String keyClass) {
        this.keyClass = keyClass;
    }

    public String getArea() {
        return area;
    }

    public NClass area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDivision() {
        return division;
    }

    public NClass division(String division) {
        this.division = division;
        return this;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Double getUnits() {
        return units;
    }

    public NClass units(Double units) {
        this.units = units;
        return this;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    public Integer getnOrder() {
        return nOrder;
    }

    public NClass nOrder(Integer nOrder) {
        this.nOrder = nOrder;
        return this;
    }

    public void setnOrder(Integer nOrder) {
        this.nOrder = nOrder;
    }

    public Career getCareer() {
        return career;
    }

    public NClass career(Career career) {
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
        if (!(o instanceof NClass)) {
            return false;
        }
        return id != null && id.equals(((NClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NClass{"
                + "id=" + getId()
                + ", name='" + getName() + "'"
                + ", keyClass='" + getKeyClass() + "'"
                + ", area='" + getArea() + "'"
                + ", division='" + getDivision() + "'"
                + ", units=" + getUnits()
                + ", nOrder=" + getnOrder()
                + "}";
    }
}
