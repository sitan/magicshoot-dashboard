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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "reseller_id")
    private Integer resellerId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "billing_adress_1")
    private String billingAdress1;

    @Column(name = "billing_adress_2")
    private String billingAdress2;

    @Column(name = "billing_zip")
    private String billingZip;

    @Column(name = "billing_place")
    private String billingPlace;

    @Column(name = "billing_country")
    private String billingCountry;

    @Column(name = "billing_email")
    private String billingEmail;

    @Column(name = "billing_phone")
    private String billingPhone;

    @Column(name = "shipping_adress_1")
    private String shippingAdress1;

    @Column(name = "shipping_adress_2")
    private String shippingAdress2;

    @Column(name = "shipping_zip")
    private String shippingZip;

    @Column(name = "shipping_place")
    private String shippingPlace;

    @Column(name = "shipping_country")
    private String shippingCountry;

    @Column(name = "shipping_email")
    private String shippingEmail;

    @Column(name = "shipping_phone")
    private String shippingPhone;

    @Column(name = "vat_percentage")
    private Float vatPercentage;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "company_created_at")
    private LocalDate companyCreatedAt;

    @Column(name = "company_modified_at")
    private LocalDate companyModifiedAt;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "companies", allowSetters = true)
    private Reseller reseller;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Company companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getResellerId() {
        return resellerId;
    }

    public Company resellerId(Integer resellerId) {
        this.resellerId = resellerId;
        return this;
    }

    public void setResellerId(Integer resellerId) {
        this.resellerId = resellerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillingAdress1() {
        return billingAdress1;
    }

    public Company billingAdress1(String billingAdress1) {
        this.billingAdress1 = billingAdress1;
        return this;
    }

    public void setBillingAdress1(String billingAdress1) {
        this.billingAdress1 = billingAdress1;
    }

    public String getBillingAdress2() {
        return billingAdress2;
    }

    public Company billingAdress2(String billingAdress2) {
        this.billingAdress2 = billingAdress2;
        return this;
    }

    public void setBillingAdress2(String billingAdress2) {
        this.billingAdress2 = billingAdress2;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public Company billingZip(String billingZip) {
        this.billingZip = billingZip;
        return this;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getBillingPlace() {
        return billingPlace;
    }

    public Company billingPlace(String billingPlace) {
        this.billingPlace = billingPlace;
        return this;
    }

    public void setBillingPlace(String billingPlace) {
        this.billingPlace = billingPlace;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public Company billingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
        return this;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public Company billingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
        return this;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public Company billingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
        return this;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getShippingAdress1() {
        return shippingAdress1;
    }

    public Company shippingAdress1(String shippingAdress1) {
        this.shippingAdress1 = shippingAdress1;
        return this;
    }

    public void setShippingAdress1(String shippingAdress1) {
        this.shippingAdress1 = shippingAdress1;
    }

    public String getShippingAdress2() {
        return shippingAdress2;
    }

    public Company shippingAdress2(String shippingAdress2) {
        this.shippingAdress2 = shippingAdress2;
        return this;
    }

    public void setShippingAdress2(String shippingAdress2) {
        this.shippingAdress2 = shippingAdress2;
    }

    public String getShippingZip() {
        return shippingZip;
    }

    public Company shippingZip(String shippingZip) {
        this.shippingZip = shippingZip;
        return this;
    }

    public void setShippingZip(String shippingZip) {
        this.shippingZip = shippingZip;
    }

    public String getShippingPlace() {
        return shippingPlace;
    }

    public Company shippingPlace(String shippingPlace) {
        this.shippingPlace = shippingPlace;
        return this;
    }

    public void setShippingPlace(String shippingPlace) {
        this.shippingPlace = shippingPlace;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public Company shippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
        return this;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingEmail() {
        return shippingEmail;
    }

    public Company shippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
        return this;
    }

    public void setShippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public Company shippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
        return this;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public Float getVatPercentage() {
        return vatPercentage;
    }

    public Company vatPercentage(Float vatPercentage) {
        this.vatPercentage = vatPercentage;
        return this;
    }

    public void setVatPercentage(Float vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public Company vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public LocalDate getCompanyCreatedAt() {
        return companyCreatedAt;
    }

    public Company companyCreatedAt(LocalDate companyCreatedAt) {
        this.companyCreatedAt = companyCreatedAt;
        return this;
    }

    public void setCompanyCreatedAt(LocalDate companyCreatedAt) {
        this.companyCreatedAt = companyCreatedAt;
    }

    public LocalDate getCompanyModifiedAt() {
        return companyModifiedAt;
    }

    public Company companyModifiedAt(LocalDate companyModifiedAt) {
        this.companyModifiedAt = companyModifiedAt;
        return this;
    }

    public void setCompanyModifiedAt(LocalDate companyModifiedAt) {
        this.companyModifiedAt = companyModifiedAt;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Company events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Company addEvent(Event event) {
        this.events.add(event);
        event.setCompany(this);
        return this;
    }

    public Company removeEvent(Event event) {
        this.events.remove(event);
        event.setCompany(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Company customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Company addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setCompany(this);
        return this;
    }

    public Company removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setCompany(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Company contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Company addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setCompany(this);
        return this;
    }

    public Company removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setCompany(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Reseller getReseller() {
        return reseller;
    }

    public Company reseller(Reseller reseller) {
        this.reseller = reseller;
        return this;
    }

    public void setReseller(Reseller reseller) {
        this.reseller = reseller;
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
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", resellerId=" + getResellerId() +
            ", companyName='" + getCompanyName() + "'" +
            ", billingAdress1='" + getBillingAdress1() + "'" +
            ", billingAdress2='" + getBillingAdress2() + "'" +
            ", billingZip='" + getBillingZip() + "'" +
            ", billingPlace='" + getBillingPlace() + "'" +
            ", billingCountry='" + getBillingCountry() + "'" +
            ", billingEmail='" + getBillingEmail() + "'" +
            ", billingPhone='" + getBillingPhone() + "'" +
            ", shippingAdress1='" + getShippingAdress1() + "'" +
            ", shippingAdress2='" + getShippingAdress2() + "'" +
            ", shippingZip='" + getShippingZip() + "'" +
            ", shippingPlace='" + getShippingPlace() + "'" +
            ", shippingCountry='" + getShippingCountry() + "'" +
            ", shippingEmail='" + getShippingEmail() + "'" +
            ", shippingPhone='" + getShippingPhone() + "'" +
            ", vatPercentage=" + getVatPercentage() +
            ", vatNumber='" + getVatNumber() + "'" +
            ", companyCreatedAt='" + getCompanyCreatedAt() + "'" +
            ", companyModifiedAt='" + getCompanyModifiedAt() + "'" +
            "}";
    }
}
