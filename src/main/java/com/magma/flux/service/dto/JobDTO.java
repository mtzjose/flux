package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Job} entity.
 */
public class JobDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @NotNull
    private Long companyId;

    @NotNull
    private Long jobPositionId;

    private CompanyDTO companyId;

    private CompanyPositionDTO jobPositionId;

    private CompanyDTO companyId;

    private CompanyPositionDTO jobPositionId;

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

    public Long getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(Long jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public CompanyDTO getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyDTO companyId) {
        this.companyId = companyId;
    }

    public CompanyPositionDTO getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(CompanyPositionDTO jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public CompanyDTO getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyDTO companyId) {
        this.companyId = companyId;
    }

    public CompanyPositionDTO getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(CompanyPositionDTO jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDTO)) {
            return false;
        }

        JobDTO jobDTO = (JobDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", jobPositionId=" + getJobPositionId() +
            ", companyId=" + getCompanyId() +
            ", jobPositionId=" + getJobPositionId() +
            ", companyId=" + getCompanyId() +
            ", jobPositionId=" + getJobPositionId() +
            "}";
    }
}
