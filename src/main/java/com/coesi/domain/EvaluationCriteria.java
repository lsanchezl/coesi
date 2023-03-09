package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EvaluationCriteria.
 */
@Entity
@Table(name = "evaluation_criteria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @NotNull
    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "evaluationCriteria", allowSetters = true)
    private PerformanceIndicator performanceIndicator;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "evaluationCriteria", allowSetters = true)
    private NGroup nGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public EvaluationCriteria description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentage() {
        return percentage;
    }

    public EvaluationCriteria percentage(Double percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public EvaluationCriteria deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PerformanceIndicator getPerformanceIndicator() {
        return performanceIndicator;
    }

    public EvaluationCriteria performanceIndicator(PerformanceIndicator performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
        return this;
    }

    public void setPerformanceIndicator(PerformanceIndicator performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
    }

    public NGroup getNGroup() {
        return nGroup;
    }

    public EvaluationCriteria nGroup(NGroup nGroup) {
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
        if (!(o instanceof EvaluationCriteria)) {
            return false;
        }
        return id != null && id.equals(((EvaluationCriteria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCriteria{"
                + "id=" + getId()
                + ", description='" + getDescription() + "'"
                + ", percentage=" + getPercentage()
                + ", deliveryDate='" + getDeliveryDate() + "'"
                + "}";
    }
}
