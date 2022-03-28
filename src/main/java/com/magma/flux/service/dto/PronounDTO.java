package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Pronoun} entity.
 */
public class PronounDTO implements Serializable {

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
        if (!(o instanceof PronounDTO)) {
            return false;
        }

        PronounDTO pronounDTO = (PronounDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pronounDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PronounDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
