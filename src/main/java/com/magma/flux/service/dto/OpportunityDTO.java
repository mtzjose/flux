package com.magma.flux.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.magma.flux.domain.Opportunity} entity.
 */
public class OpportunityDTO implements Serializable {

    private Long id;

    private Long companyId;

    private LocalDate applyDate;

    private Long contactSourceId;

    private Long processStageId;

    private CompanyDTO companyId;

    private ContactSourceDTO contactSourceId;

    private ProcessStageDTO processStageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public Long getContactSourceId() {
        return contactSourceId;
    }

    public void setContactSourceId(Long contactSourceId) {
        this.contactSourceId = contactSourceId;
    }

    public Long getProcessStageId() {
        return processStageId;
    }

    public void setProcessStageId(Long processStageId) {
        this.processStageId = processStageId;
    }

    public CompanyDTO getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyDTO companyId) {
        this.companyId = companyId;
    }

    public ContactSourceDTO getContactSourceId() {
        return contactSourceId;
    }

    public void setContactSourceId(ContactSourceDTO contactSourceId) {
        this.contactSourceId = contactSourceId;
    }

    public ProcessStageDTO getProcessStageId() {
        return processStageId;
    }

    public void setProcessStageId(ProcessStageDTO processStageId) {
        this.processStageId = processStageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpportunityDTO)) {
            return false;
        }

        OpportunityDTO opportunityDTO = (OpportunityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, opportunityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpportunityDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", applyDate='" + getApplyDate() + "'" +
            ", contactSourceId=" + getContactSourceId() +
            ", processStageId=" + getProcessStageId() +
            ", companyId=" + getCompanyId() +
            ", contactSourceId=" + getContactSourceId() +
            ", processStageId=" + getProcessStageId() +
            "}";
    }
}
