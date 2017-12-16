package eu.dreamix.four_for_belot.service;

import eu.dreamix.four_for_belot.service.dto.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Notification.
 */
public interface NotificationService {

    /**
     * Save a notification.
     *
     * @param notificationDTO the entity to save
     * @return the persisted entity
     */
    NotificationDTO save(NotificationDTO notificationDTO);

    /**
     * Get all the notifications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" notification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NotificationDTO findOne(Long id);

    /**
     * Delete the "id" notification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
