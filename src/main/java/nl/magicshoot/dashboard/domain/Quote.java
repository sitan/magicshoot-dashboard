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
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quote_id")
    private Integer quoteId;

    @Column(name = "contact_id")
    private Integer contactId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "quote_description")
    private String quoteDescription;

    @Column(name = "quote_price")
    private Float quotePrice;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "quote_created_at")
    private LocalDate quoteCreatedAt;

    @Column(name = "quote_modified_at")
    private LocalDate quoteModifiedAt;

    @OneToMany(mappedBy = "quote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
    private Contact contact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public Quote quoteId(Integer quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public Quote contactId(Integer contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Quote companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getQuoteDescription() {
        return quoteDescription;
    }

    public Quote quoteDescription(String quoteDescription) {
        this.quoteDescription = quoteDescription;
        return this;
    }

    public void setQuoteDescription(String quoteDescription) {
        this.quoteDescription = quoteDescription;
    }

    public Float getQuotePrice() {
        return quotePrice;
    }

    public Quote quotePrice(Float quotePrice) {
        this.quotePrice = quotePrice;
        return this;
    }

    public void setQuotePrice(Float quotePrice) {
        this.quotePrice = quotePrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Quote orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getQuoteCreatedAt() {
        return quoteCreatedAt;
    }

    public Quote quoteCreatedAt(LocalDate quoteCreatedAt) {
        this.quoteCreatedAt = quoteCreatedAt;
        return this;
    }

    public void setQuoteCreatedAt(LocalDate quoteCreatedAt) {
        this.quoteCreatedAt = quoteCreatedAt;
    }

    public LocalDate getQuoteModifiedAt() {
        return quoteModifiedAt;
    }

    public Quote quoteModifiedAt(LocalDate quoteModifiedAt) {
        this.quoteModifiedAt = quoteModifiedAt;
        return this;
    }

    public void setQuoteModifiedAt(LocalDate quoteModifiedAt) {
        this.quoteModifiedAt = quoteModifiedAt;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Quote invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Quote addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setQuote(this);
        return this;
    }

    public Quote removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setQuote(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Contact getContact() {
        return contact;
    }

    public Quote contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", quoteId=" + getQuoteId() +
            ", contactId=" + getContactId() +
            ", companyId=" + getCompanyId() +
            ", quoteDescription='" + getQuoteDescription() + "'" +
            ", quotePrice=" + getQuotePrice() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", quoteCreatedAt='" + getQuoteCreatedAt() + "'" +
            ", quoteModifiedAt='" + getQuoteModifiedAt() + "'" +
            "}";
    }
}
