package com.magma.flux.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @Lob
    private String meta;

    @Lob
    private byte[] logo;

    private String logoContentType;
    private String color;

    private String name;

    private String legalName;

    private String oneLiner;

    @Lob
    private String description;

    private LocalDate foundingDate;

    @Lob
    private String socialLinks;

    private Long addressId;

    private Long employeeRange;

    private AddressDTO addressId;

    private EmployeeRangeDTO employeeRange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getOneLiner() {
        return oneLiner;
    }

    public void setOneLiner(String oneLiner) {
        this.oneLiner = oneLiner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public String getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(String socialLinks) {
        this.socialLinks = socialLinks;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getEmployeeRange() {
        return employeeRange;
    }

    public void setEmployeeRange(Long employeeRange) {
        this.employeeRange = employeeRange;
    }

    public AddressDTO getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressDTO addressId) {
        this.addressId = addressId;
    }

    public EmployeeRangeDTO getEmployeeRange() {
        return employeeRange;
    }

    public void setEmployeeRange(EmployeeRangeDTO employeeRange) {
        this.employeeRange = employeeRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", meta='" + getMeta() + "'" +
            ", logo='" + getLogo() + "'" +
            ", color='" + getColor() + "'" +
            ", name='" + getName() + "'" +
            ", legalName='" + getLegalName() + "'" +
            ", oneLiner='" + getOneLiner() + "'" +
            ", description='" + getDescription() + "'" +
            ", foundingDate='" + getFoundingDate() + "'" +
            ", socialLinks='" + getSocialLinks() + "'" +
            ", addressId=" + getAddressId() +
            ", employeeRange=" + getEmployeeRange() +
            ", addressId=" + getAddressId() +
            ", employeeRange=" + getEmployeeRange() +
            "}";
    }
}
