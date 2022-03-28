package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.ProcessStage} entity.
 */
public class ProcessStageDTO implements Serializable {

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
        if (!(o instanceof ProcessStageDTO)) {
            return false;
        }

        ProcessStageDTO processStageDTO = (ProcessStageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processStageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessStageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
