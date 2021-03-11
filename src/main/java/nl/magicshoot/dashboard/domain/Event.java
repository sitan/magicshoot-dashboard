package nl.magicshoot.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import nl.magicshoot.dashboard.domain.enumeration.EventType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_start_date")
    private LocalDate eventStartDate;

    @Column(name = "event_end_date")
    private LocalDate eventEndDate;

    @Column(name = "event_start_time")
    private LocalDate eventStartTime;

    @Column(name = "event_end_time")
    private LocalDate eventEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @ManyToOne
    @JsonIgnoreProperties(value = "events", allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Event eventId(Integer eventId) {
        this.eventId = eventId;
        return this;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Event companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEventName() {
        return eventName;
    }

    public Event eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventStartDate() {
        return eventStartDate;
    }

    public Event eventStartDate(LocalDate eventStartDate) {
        this.eventStartDate = eventStartDate;
        return this;
    }

    public void setEventStartDate(LocalDate eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public LocalDate getEventEndDate() {
        return eventEndDate;
    }

    public Event eventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
        return this;
    }

    public void setEventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public LocalDate getEventStartTime() {
        return eventStartTime;
    }

    public Event eventStartTime(LocalDate eventStartTime) {
        this.eventStartTime = eventStartTime;
        return this;
    }

    public void setEventStartTime(LocalDate eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDate getEventEndTime() {
        return eventEndTime;
    }

    public Event eventEndTime(LocalDate eventEndTime) {
        this.eventEndTime = eventEndTime;
        return this;
    }

    public void setEventEndTime(LocalDate eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Event eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Company getCompany() {
        return company;
    }

    public Event company(Company company) {
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
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", eventId=" + getEventId() +
            ", companyId=" + getCompanyId() +
            ", eventName='" + getEventName() + "'" +
            ", eventStartDate='" + getEventStartDate() + "'" +
            ", eventEndDate='" + getEventEndDate() + "'" +
            ", eventStartTime='" + getEventStartTime() + "'" +
            ", eventEndTime='" + getEventEndTime() + "'" +
            ", eventType='" + getEventType() + "'" +
            "}";
    }
}
