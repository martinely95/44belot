package eu.dreamix.four_for_belot.service.mapper;

import eu.dreamix.four_for_belot.domain.*;
import eu.dreamix.four_for_belot.service.dto.GameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProfileMapper.class, LocationMapper.class})
public interface GameMapper extends EntityMapper<GameDTO, Game> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "creatorProfile.id", target = "creatorProfileId")
    GameDTO toDto(Game game); 

    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "creatorProfileId", target = "creatorProfile")
    Game toEntity(GameDTO gameDTO);

    default Game fromId(Long id) {
        if (id == null) {
            return null;
        }
        Game game = new Game();
        game.setId(id);
        return game;
    }
}
