package com.coesi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ConceptIncome.
 */
@Entity
@Table(name = "concept_income")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConceptIncome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 20)
    @Column(name = "key_concept", length = 20)
    private String keyConcept;

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

    public ConceptIncome name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyConcept() {
        return keyConcept;
    }

    public ConceptIncome keyConcept(String keyConcept) {
        this.keyConcept = keyConcept;
        return this;
    }

    public void setKeyConcept(String keyConcept) {
        this.keyConcept = keyConcept;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConceptIncome)) {
            return false;
        }
        return id != null && id.equals(((ConceptIncome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConceptIncome{"
                + "id=" + getId()
                + ", name='" + getName() + "'"
                + ", keyConcept='" + getKeyConcept() + "'"
                + "}";
    }
}
