package eu.dreamix.four_for_belot.service;

import eu.dreamix.four_for_belot.service.dto.RatingDTO;
import java.util.List;

/**
 * Service Interface for managing Rating.
 */
public interface RatingService {

    /**
     * Save a rating.
     *
     * @param ratingDTO the entity to save
     * @return the persisted entity
     */
    RatingDTO save(RatingDTO ratingDTO);

    /**
     * Get all the ratings.
     *
     * @return the list of entities
     */
    List<RatingDTO> findAll();

    /**
     * Get the "id" rating.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RatingDTO findOne(Long id);

    /**
     * Delete the "id" rating.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
