package nl.magicshoot.dashboard.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Admin.
 */
@Entity
@Table(name = "admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "admin_created_at")
    private LocalDate adminCreatedAt;

    @Column(name = "admin_modified_at")
    private LocalDate adminModifiedAt;

    @OneToMany(mappedBy = "admin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Reseller> resellers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public Admin adminId(Integer adminId) {
        this.adminId = adminId;
        return this;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public Admin adminName(String adminName) {
        this.adminName = adminName;
        return this;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public Admin adminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
        return this;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public Admin adminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
        return this;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public LocalDate getAdminCreatedAt() {
        return adminCreatedAt;
    }

    public Admin adminCreatedAt(LocalDate adminCreatedAt) {
        this.adminCreatedAt = adminCreatedAt;
        return this;
    }

    public void setAdminCreatedAt(LocalDate adminCreatedAt) {
        this.adminCreatedAt = adminCreatedAt;
    }

    public LocalDate getAdminModifiedAt() {
        return adminModifiedAt;
    }

    public Admin adminModifiedAt(LocalDate adminModifiedAt) {
        this.adminModifiedAt = adminModifiedAt;
        return this;
    }

    public void setAdminModifiedAt(LocalDate adminModifiedAt) {
        this.adminModifiedAt = adminModifiedAt;
    }

    public Set<Reseller> getResellers() {
        return resellers;
    }

    public Admin resellers(Set<Reseller> resellers) {
        this.resellers = resellers;
        return this;
    }

    public Admin addReseller(Reseller reseller) {
        this.resellers.add(reseller);
        reseller.setAdmin(this);
        return this;
    }

    public Admin removeReseller(Reseller reseller) {
        this.resellers.remove(reseller);
        reseller.setAdmin(null);
        return this;
    }

    public void setResellers(Set<Reseller> resellers) {
        this.resellers = resellers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        return id != null && id.equals(((Admin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            ", adminId=" + getAdminId() +
            ", adminName='" + getAdminName() + "'" +
            ", adminEmail='" + getAdminEmail() + "'" +
            ", adminPassword='" + getAdminPassword() + "'" +
            ", adminCreatedAt='" + getAdminCreatedAt() + "'" +
            ", adminModifiedAt='" + getAdminModifiedAt() + "'" +
            "}";
    }
}
