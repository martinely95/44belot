package eu.dreamix.four_for_belot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "required_people_count", nullable = false)
    private Integer requiredPeopleCount;

    @NotNull
    @Column(name = "currently_avalable_people", nullable = false)
    private Integer currentlyAvalablePeople;

    @NotNull
    @Column(name = "created_date_time", nullable = false)
    private ZonedDateTime createdDateTime;

    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    @NotNull
    @Column(name = "requires_approval_for_joining", nullable = false)
    private Boolean requiresApprovalForJoining;

    @NotNull
    @Column(name = "is_available_for_joining", nullable = false)
    private Boolean isAvailableForJoining;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "playedGame")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "game_categories",
               joinColumns = @JoinColumn(name="games_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="categories_id", referencedColumnName="id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "game_players_profiles",
               joinColumns = @JoinColumn(name="games_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="players_profiles_id", referencedColumnName="id"))
    private Set<Profile> playersProfiles = new HashSet<>();

    @ManyToOne
    private Location location;

    @ManyToOne
    private Profile creatorProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Game description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRequiredPeopleCount() {
        return requiredPeopleCount;
    }

    public Game requiredPeopleCount(Integer requiredPeopleCount) {
        this.requiredPeopleCount = requiredPeopleCount;
        return this;
    }

    public void setRequiredPeopleCount(Integer requiredPeopleCount) {
        this.requiredPeopleCount = requiredPeopleCount;
    }

    public Integer getCurrentlyAvalablePeople() {
        return currentlyAvalablePeople;
    }

    public Game currentlyAvalablePeople(Integer currentlyAvalablePeople) {
        this.currentlyAvalablePeople = currentlyAvalablePeople;
        return this;
    }

    public void setCurrentlyAvalablePeople(Integer currentlyAvalablePeople) {
        this.currentlyAvalablePeople = currentlyAvalablePeople;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public Game createdDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public Game startDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public Game endDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean isRequiresApprovalForJoining() {
        return requiresApprovalForJoining;
    }

    public Game requiresApprovalForJoining(Boolean requiresApprovalForJoining) {
        this.requiresApprovalForJoining = requiresApprovalForJoining;
        return this;
    }

    public void setRequiresApprovalForJoining(Boolean requiresApprovalForJoining) {
        this.requiresApprovalForJoining = requiresApprovalForJoining;
    }

    public Boolean isIsAvailableForJoining() {
        return isAvailableForJoining;
    }

    public Game isAvailableForJoining(Boolean isAvailableForJoining) {
        this.isAvailableForJoining = isAvailableForJoining;
        return this;
    }

    public void setIsAvailableForJoining(Boolean isAvailableForJoining) {
        this.isAvailableForJoining = isAvailableForJoining;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Game notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Game addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setGame(this);
        return this;
    }

    public Game removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setGame(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Game ratings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Game addRatings(Rating rating) {
        this.ratings.add(rating);
        rating.setPlayedGame(this);
        return this;
    }

    public Game removeRatings(Rating rating) {
        this.ratings.remove(rating);
        rating.setPlayedGame(null);
        return this;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Game categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Game addCategories(Category category) {
        this.categories.add(category);
        category.getGamesWithThisCategories().add(this);
        return this;
    }

    public Game removeCategories(Category category) {
        this.categories.remove(category);
        category.getGamesWithThisCategories().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Profile> getPlayersProfiles() {
        return playersProfiles;
    }

    public Game playersProfiles(Set<Profile> profiles) {
        this.playersProfiles = profiles;
        return this;
    }

    public Game addPlayersProfiles(Profile profile) {
        this.playersProfiles.add(profile);
        profile.getPlayedGames().add(this);
        return this;
    }

    public Game removePlayersProfiles(Profile profile) {
        this.playersProfiles.remove(profile);
        profile.getPlayedGames().remove(this);
        return this;
    }

    public void setPlayersProfiles(Set<Profile> profiles) {
        this.playersProfiles = profiles;
    }

    public Location getLocation() {
        return location;
    }

    public Game location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Profile getCreatorProfile() {
        return creatorProfile;
    }

    public Game creatorProfile(Profile profile) {
        this.creatorProfile = profile;
        return this;
    }

    public void setCreatorProfile(Profile profile) {
        this.creatorProfile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
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
