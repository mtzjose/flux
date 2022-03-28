package com.magma.flux.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.CompanyCategories} entity.
 */
public class CompanyCategoriesDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @NotNull
    private Long companyId;

    @NotNull
    private Long categoryId;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyCategoriesDTO)) {
            return false;
        }

        CompanyCategoriesDTO companyCategoriesDTO = (CompanyCategoriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyCategoriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCategoriesDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
