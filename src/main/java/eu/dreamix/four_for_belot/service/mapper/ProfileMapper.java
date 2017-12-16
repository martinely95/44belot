package eu.dreamix.four_for_belot.service.mapper;

import eu.dreamix.four_for_belot.domain.*;
import eu.dreamix.four_for_belot.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    ProfileDTO toDto(Profile profile); 

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "createdGames", ignore = true)
    @Mapping(target = "givenRatings", ignore = true)
    @Mapping(target = "receivedRatings", ignore = true)
    @Mapping(target = "playedGames", ignore = true)
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
