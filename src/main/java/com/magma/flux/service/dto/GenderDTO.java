package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Gender} entity.
 */
public class GenderDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenderDTO)) {
            return false;
        }

        GenderDTO genderDTO = (GenderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenderDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
