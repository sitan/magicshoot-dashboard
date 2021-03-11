package nl.magicshoot.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_created_at")
    private LocalDate contactCreatedAt;

    @Column(name = "contact_modified_at")
    private LocalDate contactModifiedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Customer customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Customer companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Customer contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LocalDate getContactCreatedAt() {
        return contactCreatedAt;
    }

    public Customer contactCreatedAt(LocalDate contactCreatedAt) {
        this.contactCreatedAt = contactCreatedAt;
        return this;
    }

    public void setContactCreatedAt(LocalDate contactCreatedAt) {
        this.contactCreatedAt = contactCreatedAt;
    }

    public LocalDate getContactModifiedAt() {
        return contactModifiedAt;
    }

    public Customer contactModifiedAt(LocalDate contactModifiedAt) {
        this.contactModifiedAt = contactModifiedAt;
        return this;
    }

    public void setContactModifiedAt(LocalDate contactModifiedAt) {
        this.contactModifiedAt = contactModifiedAt;
    }

    public Company getCompany() {
        return company;
    }

    public Customer company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", companyId=" + getCompanyId() +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactCreatedAt='" + getContactCreatedAt() + "'" +
            ", contactModifiedAt='" + getContactModifiedAt() + "'" +
            "}";
    }
}
