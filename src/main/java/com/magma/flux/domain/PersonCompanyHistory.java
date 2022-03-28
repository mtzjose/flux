package com.magma.flux.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonCompanyHistory.
 */
@Entity
@Table(name = "person_company_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonCompanyHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "investor")
    private Boolean investor;

    @Column(name = "founder")
    private Boolean founder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonCompanyHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public PersonCompanyHistory companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPersonId() {
        return this.personId;
    }

    public PersonCompanyHistory personId(Long personId) {
        this.setPersonId(personId);
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Boolean getInvestor() {
        return this.investor;
    }

    public PersonCompanyHistory investor(Boolean investor) {
        this.setInvestor(investor);
        return this;
    }

    public void setInvestor(Boolean investor) {
        this.investor = investor;
    }

    public Boolean getFounder() {
        return this.founder;
    }

    public PersonCompanyHistory founder(Boolean founder) {
        this.setFounder(founder);
        return this;
    }

    public void setFounder(Boolean founder) {
        this.founder = founder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonCompanyHistory)) {
            return false;
        }
        return id != null && id.equals(((PersonCompanyHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonCompanyHistory{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", personId=" + getPersonId() +
            ", investor='" + getInvestor() + "'" +
            ", founder='" + getFounder() + "'" +
            "}";
    }
}
