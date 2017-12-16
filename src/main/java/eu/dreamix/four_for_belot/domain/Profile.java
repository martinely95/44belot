package eu.dreamix.four_for_belot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import eu.dreamix.four_for_belot.domain.enumeration.Language;

import eu.dreamix.four_for_belot.domain.enumeration.Sex;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "games_played")
    private Long gamesPlayed;

    @Column(name = "games_won")
    private Long gamesWon;

    @Column(name = "games_lost")
    private Long gamesLost;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "profileToBeNotified")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "creatorProfile")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> createdGames = new HashSet<>();

    @OneToMany(mappedBy = "giverProfile")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> givenRatings = new HashSet<>();

    @OneToMany(mappedBy = "receiverProfile")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> receivedRatings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profile_categories_of_interest",
               joinColumns = @JoinColumn(name="profiles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="categories_of_interests_id", referencedColumnName="id"))
    private Set<Category> categoriesOfInterests = new HashSet<>();

    @ManyToMany(mappedBy = "playersProfiles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> playedGames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public Profile language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Sex getSex() {
        return sex;
    }

    public Profile sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public Profile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public Profile gamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Long getGamesWon() {
        return gamesWon;
    }

    public Profile gamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
        return this;
    }

    public void setGamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Long getGamesLost() {
        return gamesLost;
    }

    public Profile gamesLost(Long gamesLost) {
        this.gamesLost = gamesLost;
        return this;
    }

    public void setGamesLost(Long gamesLost) {
        this.gamesLost = gamesLost;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Profile notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Profile addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setProfileToBeNotified(this);
        return this;
    }

    public Profile removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setProfileToBeNotified(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Game> getCreatedGames() {
        return createdGames;
    }

    public Profile createdGames(Set<Game> games) {
        this.createdGames = games;
        return this;
    }

    public Profile addCreatedGames(Game game) {
        this.createdGames.add(game);
        game.setCreatorProfile(this);
        return this;
    }

    public Profile removeCreatedGames(Game game) {
        this.createdGames.remove(game);
        game.setCreatorProfile(null);
        return this;
    }

    public void setCreatedGames(Set<Game> games) {
        this.createdGames = games;
    }

    public Set<Rating> getGivenRatings() {
        return givenRatings;
    }

    public Profile givenRatings(Set<Rating> ratings) {
        this.givenRatings = ratings;
        return this;
    }

    public Profile addGivenRatings(Rating rating) {
        this.givenRatings.add(rating);
        rating.setGiverProfile(this);
        return this;
    }

    public Profile removeGivenRatings(Rating rating) {
        this.givenRatings.remove(rating);
        rating.setGiverProfile(null);
        return this;
    }

    public void setGivenRatings(Set<Rating> ratings) {
        this.givenRatings = ratings;
    }

    public Set<Rating> getReceivedRatings() {
        return receivedRatings;
    }

    public Profile receivedRatings(Set<Rating> ratings) {
        this.receivedRatings = ratings;
        return this;
    }

    public Profile addReceivedRatings(Rating rating) {
        this.receivedRatings.add(rating);
        rating.setReceiverProfile(this);
        return this;
    }

    public Profile removeReceivedRatings(Rating rating) {
        this.receivedRatings.remove(rating);
        rating.setReceiverProfile(null);
        return this;
    }

    public void setReceivedRatings(Set<Rating> ratings) {
        this.receivedRatings = ratings;
    }

    public Set<Category> getCategoriesOfInterests() {
        return categoriesOfInterests;
    }

    public Profile categoriesOfInterests(Set<Category> categories) {
        this.categoriesOfInterests = categories;
        return this;
    }

    public Profile addCategoriesOfInterest(Category category) {
        this.categoriesOfInterests.add(category);
        category.getInterestedProfiles().add(this);
        return this;
    }

    public Profile removeCategoriesOfInterest(Category category) {
        this.categoriesOfInterests.remove(category);
        category.getInterestedProfiles().remove(this);
        return this;
    }

    public void setCategoriesOfInterests(Set<Category> categories) {
        this.categoriesOfInterests = categories;
    }

    public Set<Game> getPlayedGames() {
        return playedGames;
    }

    public Profile playedGames(Set<Game> games) {
        this.playedGames = games;
        return this;
    }

    public Profile addPlayedGames(Game game) {
        this.playedGames.add(game);
        game.getPlayersProfiles().add(this);
        return this;
    }

    public Profile removePlayedGames(Game game) {
        this.playedGames.remove(game);
        game.getPlayersProfiles().remove(this);
        return this;
    }

    public void setPlayedGames(Set<Game> games) {
        this.playedGames = games;
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", sex='" + getSex() + "'" +
            ", age=" + getAge() +
            ", gamesPlayed=" + getGamesPlayed() +
            ", gamesWon=" + getGamesWon() +
            ", gamesLost=" + getGamesLost() +
            "}";
    }
}
