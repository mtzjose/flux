package com.magma.flux.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompanyPosition.
 */
@Entity
@Table(name = "company_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "jobPositionId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "companyId", "jobPositionId", "companyId", "jobPositionId" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyPosition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CompanyPosition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.setJobPositionId(null));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.setJobPositionId(this));
        }
        this.jobs = jobs;
    }

    public CompanyPosition jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public CompanyPosition addJob(Job job) {
        this.jobs.add(job);
        job.setJobPositionId(this);
        return this;
    }

    public CompanyPosition removeJob(Job job) {
        this.jobs.remove(job);
        job.setJobPositionId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyPosition)) {
            return false;
        }
        return id != null && id.equals(((CompanyPosition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyPosition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
