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
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_id")
    private Integer contactId;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "contact_telephone")
    private String contactTelephone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_password")
    private String contactPassword;

    @Column(name = "contact_created_at")
    private LocalDate contactCreatedAt;

    @Column(name = "contact_modified_at")
    private LocalDate contactModifiedAt;

    @OneToMany(mappedBy = "contact")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Quote> quotes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "contacts", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContactId() {
        return contactId;
    }

    public Contact contactId(Integer contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public Contact contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Contact companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public Contact contactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
        return this;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Contact contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPassword() {
        return contactPassword;
    }

    public Contact contactPassword(String contactPassword) {
        this.contactPassword = contactPassword;
        return this;
    }

    public void setContactPassword(String contactPassword) {
        this.contactPassword = contactPassword;
    }

    public LocalDate getContactCreatedAt() {
        return contactCreatedAt;
    }

    public Contact contactCreatedAt(LocalDate contactCreatedAt) {
        this.contactCreatedAt = contactCreatedAt;
        return this;
    }

    public void setContactCreatedAt(LocalDate contactCreatedAt) {
        this.contactCreatedAt = contactCreatedAt;
    }

    public LocalDate getContactModifiedAt() {
        return contactModifiedAt;
    }

    public Contact contactModifiedAt(LocalDate contactModifiedAt) {
        this.contactModifiedAt = contactModifiedAt;
        return this;
    }

    public void setContactModifiedAt(LocalDate contactModifiedAt) {
        this.contactModifiedAt = contactModifiedAt;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public Contact quotes(Set<Quote> quotes) {
        this.quotes = quotes;
        return this;
    }

    public Contact addQuote(Quote quote) {
        this.quotes.add(quote);
        quote.setContact(this);
        return this;
    }

    public Contact removeQuote(Quote quote) {
        this.quotes.remove(quote);
        quote.setContact(null);
        return this;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }

    public Company getCompany() {
        return company;
    }

    public Contact company(Company company) {
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
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", contactName='" + getContactName() + "'" +
            ", companyId=" + getCompanyId() +
            ", contactTelephone='" + getContactTelephone() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactPassword='" + getContactPassword() + "'" +
            ", contactCreatedAt='" + getContactCreatedAt() + "'" +
            ", contactModifiedAt='" + getContactModifiedAt() + "'" +
            "}";
    }
}
