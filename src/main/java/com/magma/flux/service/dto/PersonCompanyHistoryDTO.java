package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.PersonCompanyHistory} entity.
 */
public class PersonCompanyHistoryDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    private Long companyId;

    private Long personId;

    private Boolean investor;

    private Boolean founder;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Boolean getInvestor() {
        return investor;
    }

    public void setInvestor(Boolean investor) {
        this.investor = investor;
    }

    public Boolean getFounder() {
        return founder;
    }

    public void setFounder(Boolean founder) {
        this.founder = founder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonCompanyHistoryDTO)) {
            return false;
        }

        PersonCompanyHistoryDTO personCompanyHistoryDTO = (PersonCompanyHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personCompanyHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonCompanyHistoryDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", personId=" + getPersonId() +
            ", investor='" + getInvestor() + "'" +
            ", founder='" + getFounder() + "'" +
            "}";
    }
}
