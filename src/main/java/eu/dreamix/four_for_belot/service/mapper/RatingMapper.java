package eu.dreamix.four_for_belot.service.mapper;

import eu.dreamix.four_for_belot.domain.*;
import eu.dreamix.four_for_belot.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {GameMapper.class, ProfileMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "playedGame.id", target = "playedGameId")
    @Mapping(source = "giverProfile.id", target = "giverProfileId")
    @Mapping(source = "receiverProfile.id", target = "receiverProfileId")
    RatingDTO toDto(Rating rating); 

    @Mapping(source = "playedGameId", target = "playedGame")
    @Mapping(source = "giverProfileId", target = "giverProfile")
    @Mapping(source = "receiverProfileId", target = "receiverProfile")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
