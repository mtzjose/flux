package com.magma.flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Opportunity.
 */
@Entity
@Table(name = "opportunity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Opportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "apply_date")
    private LocalDate applyDate;

    @Column(name = "contact_source_id")
    private Long contactSourceId;

    @Column(name = "process_stage_id")
    private Long processStageId;

    @JsonIgnoreProperties(value = { "addressId", "employeeRange", "jobs" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Company companyId;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactSource contactSourceId;

    @OneToOne
    @JoinColumn(unique = true)
    private ProcessStage processStageId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opportunity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Opportunity companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getApplyDate() {
        return this.applyDate;
    }

    public Opportunity applyDate(LocalDate applyDate) {
        this.setApplyDate(applyDate);
        return this;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public Long getContactSourceId() {
        return this.contactSourceId;
    }

    public Opportunity contactSourceId(Long contactSourceId) {
        this.setContactSourceId(contactSourceId);
        return this;
    }

    public void setContactSourceId(Long contactSourceId) {
        this.contactSourceId = contactSourceId;
    }

    public Long getProcessStageId() {
        return this.processStageId;
    }

    public Opportunity processStageId(Long processStageId) {
        this.setProcessStageId(processStageId);
        return this;
    }

    public void setProcessStageId(Long processStageId) {
        this.processStageId = processStageId;
    }

    public Company getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Company company) {
        this.companyId = company;
    }

    public Opportunity companyId(Company company) {
        this.setCompanyId(company);
        return this;
    }

    public ContactSource getContactSourceId() {
        return this.contactSourceId;
    }

    public void setContactSourceId(ContactSource contactSource) {
        this.contactSourceId = contactSource;
    }

    public Opportunity contactSourceId(ContactSource contactSource) {
        this.setContactSourceId(contactSource);
        return this;
    }

    public ProcessStage getProcessStageId() {
        return this.processStageId;
    }

    public void setProcessStageId(ProcessStage processStage) {
        this.processStageId = processStage;
    }

    public Opportunity processStageId(ProcessStage processStage) {
        this.setProcessStageId(processStage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opportunity)) {
            return false;
        }
        return id != null && id.equals(((Opportunity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opportunity{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", applyDate='" + getApplyDate() + "'" +
            ", contactSourceId=" + getContactSourceId() +
            ", processStageId=" + getProcessStageId() +
            "}";
    }
}
