package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Founder} entity.
 */
public class FounderDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @NotNull
    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FounderDTO)) {
            return false;
        }

        FounderDTO founderDTO = (FounderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, founderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FounderDTO{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            "}";
    }
}
