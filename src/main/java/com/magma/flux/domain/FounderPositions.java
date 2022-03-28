package com.magma.flux.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FounderPositions.
 */
@Entity
@Table(name = "founder_positions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FounderPositions implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FounderPositions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return this.positionId;
    }

    public FounderPositions positionId(Long positionId) {
        this.setPositionId(positionId);
        return this;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public FounderPositions companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FounderPositions)) {
            return false;
        }
        return id != null && id.equals(((FounderPositions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FounderPositions{" +
            "id=" + getId() +
            ", positionId=" + getPositionId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
