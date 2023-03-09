package com.coesi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Income.
 */
@Entity
@Table(name = "income")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Income implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private User creationUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private User modificationUser;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "incomes", allowSetters = true)
    private ConceptIncome conceptIncome;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Income amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public Income comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public Income applicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Income creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public Income modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getUser() {
        return user;
    }

    public Income user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public Income creationUser(User user) {
        this.creationUser = user;
        return this;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }

    public User getModificationUser() {
        return modificationUser;
    }

    public Income modificationUser(User user) {
        this.modificationUser = user;
        return this;
    }

    public void setModificationUser(User user) {
        this.modificationUser = user;
    }

    public ConceptIncome getConceptIncome() {
        return conceptIncome;
    }

    public Income conceptIncome(ConceptIncome conceptIncome) {
        this.conceptIncome = conceptIncome;
        return this;
    }

    public void setConceptIncome(ConceptIncome conceptIncome) {
        this.conceptIncome = conceptIncome;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Income)) {
            return false;
        }
        return id != null && id.equals(((Income) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Income{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", comments='" + getComments() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
