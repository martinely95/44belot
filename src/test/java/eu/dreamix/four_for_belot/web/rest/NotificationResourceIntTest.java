package eu.dreamix.four_for_belot.web.rest;

import eu.dreamix.four_for_belot.FourForBelotApp;

import eu.dreamix.four_for_belot.domain.Notification;
import eu.dreamix.four_for_belot.repository.NotificationRepository;
import eu.dreamix.four_for_belot.service.NotificationService;
import eu.dreamix.four_for_belot.service.dto.NotificationDTO;
import eu.dreamix.four_for_belot.service.mapper.NotificationMapper;
import eu.dreamix.four_for_belot.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static eu.dreamix.four_for_belot.web.rest.TestUtil.sameInstant;
import static eu.dreamix.four_for_belot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NotificationResource REST controller.
 *
 * @see NotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FourForBelotApp.class)
public class NotificationResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotificationMockMvc;

    private Notification notification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationResource notificationResource = new NotificationResource(notificationService);
        this.restNotificationMockMvc = MockMvcBuilders.standaloneSetup(notificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .text(DEFAULT_TEXT)
            .link(DEFAULT_LINK)
            .dateTime(DEFAULT_DATE_TIME);
        return notification;
    }

    @Before
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testNotification.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNotification.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setText(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setDateTime(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))));
    }

    @Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findOne(notification.getId());
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .text(UPDATED_TEXT)
            .link(UPDATED_LINK)
            .dateTime(UPDATED_DATE_TIME);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testNotification.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNotification.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Get the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = new Notification();
        notification1.setId(1L);
        Notification notification2 = new Notification();
        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);
        notification2.setId(2L);
        assertThat(notification1).isNotEqualTo(notification2);
        notification1.setId(null);
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationDTO.class);
        NotificationDTO notificationDTO1 = new NotificationDTO();
        notificationDTO1.setId(1L);
        NotificationDTO notificationDTO2 = new NotificationDTO();
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
        notificationDTO2.setId(notificationDTO1.getId());
        assertThat(notificationDTO1).isEqualTo(notificationDTO2);
        notificationDTO2.setId(2L);
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
        notificationDTO1.setId(null);
        assertThat(notificationDTO1).isNotEqualTo(notificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(notificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(notificationMapper.fromId(null)).isNull();
    }
}
