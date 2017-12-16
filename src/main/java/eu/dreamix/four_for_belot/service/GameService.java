package eu.dreamix.four_for_belot.service;

import eu.dreamix.four_for_belot.service.dto.GameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Game.
 */
public interface GameService {

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    GameDTO save(GameDTO gameDTO);

    /**
     * Get all the games.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GameDTO> findAll(Pageable pageable);

    /**
     * Get the "id" game.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GameDTO findOne(Long id);

    /**
     * Delete the "id" game.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
