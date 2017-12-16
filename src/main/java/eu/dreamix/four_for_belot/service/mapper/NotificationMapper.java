package eu.dreamix.four_for_belot.service.mapper;

import eu.dreamix.four_for_belot.domain.*;
import eu.dreamix.four_for_belot.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {GameMapper.class, ProfileMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "profileToBeNotified.id", target = "profileToBeNotifiedId")
    NotificationDTO toDto(Notification notification); 

    @Mapping(source = "gameId", target = "game")
    @Mapping(source = "profileToBeNotifiedId", target = "profileToBeNotified")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
