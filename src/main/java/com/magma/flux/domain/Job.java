package com.magma.flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotNull
    @Column(name = "job_position_id", nullable = false)
    private Long jobPositionId;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "addressId", "employeeRange", "jobs" }, allowSetters = true)
    private Company companyId;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
    private CompanyPosition jobPositionId;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "addressId", "employeeRange", "jobs" }, allowSetters = true)
    private Company companyId;

    @ManyToOne
    @ManyToOne
    @JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
    private CompanyPosition jobPositionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Job id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Job companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getJobPositionId() {
        return this.jobPositionId;
    }

    public Job jobPositionId(Long jobPositionId) {
        this.setJobPositionId(jobPositionId);
        return this;
    }

    public void setJobPositionId(Long jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public Company getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Company company) {
        this.companyId = company;
    }

    public Job companyId(Company company) {
        this.setCompanyId(company);
        return this;
    }

    public CompanyPosition getJobPositionId() {
        return this.jobPositionId;
    }

    public void setJobPositionId(CompanyPosition companyPosition) {
        this.jobPositionId = companyPosition;
    }

    public Job jobPositionId(CompanyPosition companyPosition) {
        this.setJobPositionId(companyPosition);
        return this;
    }

    public Company getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Company company) {
        this.companyId = company;
    }

    public Job companyId(Company company) {
        this.setCompanyId(company);
        return this;
    }

    public CompanyPosition getJobPositionId() {
        return this.jobPositionId;
    }

    public void setJobPositionId(CompanyPosition companyPosition) {
        this.jobPositionId = companyPosition;
    }

    public Job jobPositionId(CompanyPosition companyPosition) {
        this.setJobPositionId(companyPosition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", jobPositionId=" + getJobPositionId() +
            "}";
    }
}
