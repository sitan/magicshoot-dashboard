package nl.magicshoot.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reseller.
 */
@Entity
@Table(name = "reseller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reseller implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reseller_id")
    private Integer resellerId;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "reseller_name")
    private String resellerName;

    @Column(name = "reseller_email")
    private String resellerEmail;

    @Column(name = "reseller_password")
    private String resellerPassword;

    @Column(name = "reseller_created_at")
    private LocalDate resellerCreatedAt;

    @Column(name = "reseller_modified_at")
    private LocalDate resellerModifiedAt;

    @OneToMany(mappedBy = "reseller")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Company> companies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "resellers", allowSetters = true)
    private Admin admin;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResellerId() {
        return resellerId;
    }

    public Reseller resellerId(Integer resellerId) {
        this.resellerId = resellerId;
        return this;
    }

    public void setResellerId(Integer resellerId) {
        this.resellerId = resellerId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public Reseller adminId(Integer adminId) {
        this.adminId = adminId;
        return this;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getResellerName() {
        return resellerName;
    }

    public Reseller resellerName(String resellerName) {
        this.resellerName = resellerName;
        return this;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
    }

    public String getResellerEmail() {
        return resellerEmail;
    }

    public Reseller resellerEmail(String resellerEmail) {
        this.resellerEmail = resellerEmail;
        return this;
    }

    public void setResellerEmail(String resellerEmail) {
        this.resellerEmail = resellerEmail;
    }

    public String getResellerPassword() {
        return resellerPassword;
    }

    public Reseller resellerPassword(String resellerPassword) {
        this.resellerPassword = resellerPassword;
        return this;
    }

    public void setResellerPassword(String resellerPassword) {
        this.resellerPassword = resellerPassword;
    }

    public LocalDate getResellerCreatedAt() {
        return resellerCreatedAt;
    }

    public Reseller resellerCreatedAt(LocalDate resellerCreatedAt) {
        this.resellerCreatedAt = resellerCreatedAt;
        return this;
    }

    public void setResellerCreatedAt(LocalDate resellerCreatedAt) {
        this.resellerCreatedAt = resellerCreatedAt;
    }

    public LocalDate getResellerModifiedAt() {
        return resellerModifiedAt;
    }

    public Reseller resellerModifiedAt(LocalDate resellerModifiedAt) {
        this.resellerModifiedAt = resellerModifiedAt;
        return this;
    }

    public void setResellerModifiedAt(LocalDate resellerModifiedAt) {
        this.resellerModifiedAt = resellerModifiedAt;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Reseller companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Reseller addCompany(Company company) {
        this.companies.add(company);
        company.setReseller(this);
        return this;
    }

    public Reseller removeCompany(Company company) {
        this.companies.remove(company);
        company.setReseller(null);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Reseller admin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reseller)) {
            return false;
        }
        return id != null && id.equals(((Reseller) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reseller{" +
            "id=" + getId() +
            ", resellerId=" + getResellerId() +
            ", adminId=" + getAdminId() +
            ", resellerName='" + getResellerName() + "'" +
            ", resellerEmail='" + getResellerEmail() + "'" +
            ", resellerPassword='" + getResellerPassword() + "'" +
            ", resellerCreatedAt='" + getResellerCreatedAt() + "'" +
            ", resellerModifiedAt='" + getResellerModifiedAt() + "'" +
            "}";
    }
}
