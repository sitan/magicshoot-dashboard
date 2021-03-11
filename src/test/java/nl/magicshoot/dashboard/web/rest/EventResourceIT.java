package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.Event;
import nl.magicshoot.dashboard.domain.enumeration.EventType;
import nl.magicshoot.dashboard.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EventResourceIT {
    private static final Integer DEFAULT_EVENT_ID = 1;
    private static final Integer UPDATED_EVENT_ID = 2;

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EVENT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EVENT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EVENT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EVENT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_END_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final EventType DEFAULT_EVENT_TYPE = EventType.ONEOFF;
    private static final EventType UPDATED_EVENT_TYPE = EventType.MULTIPLEDAYS;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .eventId(DEFAULT_EVENT_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .eventName(DEFAULT_EVENT_NAME)
            .eventStartDate(DEFAULT_EVENT_START_DATE)
            .eventEndDate(DEFAULT_EVENT_END_DATE)
            .eventStartTime(DEFAULT_EVENT_START_TIME)
            .eventEndTime(DEFAULT_EVENT_END_TIME)
            .eventType(DEFAULT_EVENT_TYPE);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .eventId(UPDATED_EVENT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .eventName(UPDATED_EVENT_NAME)
            .eventStartDate(UPDATED_EVENT_START_DATE)
            .eventEndDate(UPDATED_EVENT_END_DATE)
            .eventStartTime(UPDATED_EVENT_START_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .eventType(UPDATED_EVENT_TYPE);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        restEventMockMvc
            .perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testEvent.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvent.getEventStartDate()).isEqualTo(DEFAULT_EVENT_START_DATE);
        assertThat(testEvent.getEventEndDate()).isEqualTo(DEFAULT_EVENT_END_DATE);
        assertThat(testEvent.getEventStartTime()).isEqualTo(DEFAULT_EVENT_START_TIME);
        assertThat(testEvent.getEventEndTime()).isEqualTo(DEFAULT_EVENT_END_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventStartDate").value(hasItem(DEFAULT_EVENT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].eventEndDate").value(hasItem(DEFAULT_EVENT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].eventStartTime").value(hasItem(DEFAULT_EVENT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventEndTime").value(hasItem(DEFAULT_EVENT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME))
            .andExpect(jsonPath("$.eventStartDate").value(DEFAULT_EVENT_START_DATE.toString()))
            .andExpect(jsonPath("$.eventEndDate").value(DEFAULT_EVENT_END_DATE.toString()))
            .andExpect(jsonPath("$.eventStartTime").value(DEFAULT_EVENT_START_TIME.toString()))
            .andExpect(jsonPath("$.eventEndTime").value(DEFAULT_EVENT_END_TIME.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .eventId(UPDATED_EVENT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .eventName(UPDATED_EVENT_NAME)
            .eventStartDate(UPDATED_EVENT_START_DATE)
            .eventEndDate(UPDATED_EVENT_END_DATE)
            .eventStartTime(UPDATED_EVENT_START_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .eventType(UPDATED_EVENT_TYPE);

        restEventMockMvc
            .perform(put("/api/events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testEvent.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvent.getEventStartDate()).isEqualTo(UPDATED_EVENT_START_DATE);
        assertThat(testEvent.getEventEndDate()).isEqualTo(UPDATED_EVENT_END_DATE);
        assertThat(testEvent.getEventStartTime()).isEqualTo(UPDATED_EVENT_START_TIME);
        assertThat(testEvent.getEventEndTime()).isEqualTo(UPDATED_EVENT_END_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put("/api/events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete("/api/events/{id}", event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
