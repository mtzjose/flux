package com.magma.flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "meta")
    private String meta;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "color")
    private String color;

    @Column(name = "name")
    private String name;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "one_liner")
    private String oneLiner;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "founding_date")
    private LocalDate foundingDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "social_links")
    private String socialLinks;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "employee_range")
    private Long employeeRange;

    @OneToOne
    @JoinColumn(unique = true)
    private Address addressId;

    @OneToOne
    @JoinColumn(unique = true)
    private EmployeeRange employeeRange;

    @OneToMany(mappedBy = "companyId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "companyId", "jobPositionId", "companyId", "jobPositionId" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeta() {
        return this.meta;
    }

    public Company meta(String meta) {
        this.setMeta(meta);
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Company logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Company logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getColor() {
        return this.color;
    }

    public Company color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalName() {
        return this.legalName;
    }

    public Company legalName(String legalName) {
        this.setLegalName(legalName);
        return this;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getOneLiner() {
        return this.oneLiner;
    }

    public Company oneLiner(String oneLiner) {
        this.setOneLiner(oneLiner);
        return this;
    }

    public void setOneLiner(String oneLiner) {
        this.oneLiner = oneLiner;
    }

    public String getDescription() {
        return this.description;
    }

    public Company description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFoundingDate() {
        return this.foundingDate;
    }

    public Company foundingDate(LocalDate foundingDate) {
        this.setFoundingDate(foundingDate);
        return this;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public String getSocialLinks() {
        return this.socialLinks;
    }

    public Company socialLinks(String socialLinks) {
        this.setSocialLinks(socialLinks);
        return this;
    }

    public void setSocialLinks(String socialLinks) {
        this.socialLinks = socialLinks;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public Company addressId(Long addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getEmployeeRange() {
        return this.employeeRange;
    }

    public Company employeeRange(Long employeeRange) {
        this.setEmployeeRange(employeeRange);
        return this;
    }

    public void setEmployeeRange(Long employeeRange) {
        this.employeeRange = employeeRange;
    }

    public Address getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Address address) {
        this.addressId = address;
    }

    public Company addressId(Address address) {
        this.setAddressId(address);
        return this;
    }

    public EmployeeRange getEmployeeRange() {
        return this.employeeRange;
    }

    public void setEmployeeRange(EmployeeRange employeeRange) {
        this.employeeRange = employeeRange;
    }

    public Company employeeRange(EmployeeRange employeeRange) {
        this.setEmployeeRange(employeeRange);
        return this;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.setCompanyId(null));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.setCompanyId(this));
        }
        this.jobs = jobs;
    }

    public Company jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Company addJob(Job job) {
        this.jobs.add(job);
        job.setCompanyId(this);
        return this;
    }

    public Company removeJob(Job job) {
        this.jobs.remove(job);
        job.setCompanyId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", meta='" + getMeta() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", color='" + getColor() + "'" +
            ", name='" + getName() + "'" +
            ", legalName='" + getLegalName() + "'" +
            ", oneLiner='" + getOneLiner() + "'" +
            ", description='" + getDescription() + "'" +
            ", foundingDate='" + getFoundingDate() + "'" +
            ", socialLinks='" + getSocialLinks() + "'" +
            ", addressId=" + getAddressId() +
            ", employeeRange=" + getEmployeeRange() +
            "}";
    }
}
