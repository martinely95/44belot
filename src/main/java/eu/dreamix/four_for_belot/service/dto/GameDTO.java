package eu.dreamix.four_for_belot.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Integer requiredPeopleCount;

    @NotNull
    private Integer currentlyAvalablePeople;

    @NotNull
    private ZonedDateTime createdDateTime;

    @NotNull
    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    @NotNull
    private Boolean requiresApprovalForJoining;

    @NotNull
    private Boolean isAvailableForJoining;

    private Set<CategoryDTO> categories = new HashSet<>();

    private Set<ProfileDTO> playersProfiles = new HashSet<>();

    private Long locationId;

    private Long creatorProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRequiredPeopleCount() {
        return requiredPeopleCount;
    }

    public void setRequiredPeopleCount(Integer requiredPeopleCount) {
        this.requiredPeopleCount = requiredPeopleCount;
    }

    public Integer getCurrentlyAvalablePeople() {
        return currentlyAvalablePeople;
    }

    public void setCurrentlyAvalablePeople(Integer currentlyAvalablePeople) {
        this.currentlyAvalablePeople = currentlyAvalablePeople;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean isRequiresApprovalForJoining() {
        return requiresApprovalForJoining;
    }

    public void setRequiresApprovalForJoining(Boolean requiresApprovalForJoining) {
        this.requiresApprovalForJoining = requiresApprovalForJoining;
    }

    public Boolean isIsAvailableForJoining() {
        return isAvailableForJoining;
    }

    public void setIsAvailableForJoining(Boolean isAvailableForJoining) {
        this.isAvailableForJoining = isAvailableForJoining;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Set<ProfileDTO> getPlayersProfiles() {
        return playersProfiles;
    }

    public void setPlayersProfiles(Set<ProfileDTO> profiles) {
        this.playersProfiles = profiles;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCreatorProfileId() {
        return creatorProfileId;
    }

    public void setCreatorProfileId(Long profileId) {
        this.creatorProfileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;
        if(gameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredPeopleCount=" + getRequiredPeopleCount() +
            ", currentlyAvalablePeople=" + getCurrentlyAvalablePeople() +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", requiresApprovalForJoining='" + isRequiresApprovalForJoining() + "'" +
            ", isAvailableForJoining='" + isIsAvailableForJoining() + "'" +
            "}";
    }
}
