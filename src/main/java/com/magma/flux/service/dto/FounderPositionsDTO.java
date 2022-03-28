package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.FounderPositions} entity.
 */
public class FounderPositionsDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    private Long positionId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FounderPositionsDTO)) {
            return false;
        }

        FounderPositionsDTO founderPositionsDTO = (FounderPositionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, founderPositionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FounderPositionsDTO{" +
            "id=" + getId() +
            ", positionId=" + getPositionId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
