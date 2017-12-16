package eu.dreamix.four_for_belot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import eu.dreamix.four_for_belot.domain.enumeration.RatingScore;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_player")
    private RatingScore teamPlayer;

    @Enumerated(EnumType.STRING)
    @Column(name = "friendly")
    private RatingScore friendly;

    @Enumerated(EnumType.STRING)
    @Column(name = "leader")
    private RatingScore leader;

    @ManyToOne
    private Game playedGame;

    @ManyToOne
    private Profile giverProfile;

    @ManyToOne
    private Profile receiverProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingScore getTeamPlayer() {
        return teamPlayer;
    }

    public Rating teamPlayer(RatingScore teamPlayer) {
        this.teamPlayer = teamPlayer;
        return this;
    }

    public void setTeamPlayer(RatingScore teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    public RatingScore getFriendly() {
        return friendly;
    }

    public Rating friendly(RatingScore friendly) {
        this.friendly = friendly;
        return this;
    }

    public void setFriendly(RatingScore friendly) {
        this.friendly = friendly;
    }

    public RatingScore getLeader() {
        return leader;
    }

    public Rating leader(RatingScore leader) {
        this.leader = leader;
        return this;
    }

    public void setLeader(RatingScore leader) {
        this.leader = leader;
    }

    public Game getPlayedGame() {
        return playedGame;
    }

    public Rating playedGame(Game game) {
        this.playedGame = game;
        return this;
    }

    public void setPlayedGame(Game game) {
        this.playedGame = game;
    }

    public Profile getGiverProfile() {
        return giverProfile;
    }

    public Rating giverProfile(Profile profile) {
        this.giverProfile = profile;
        return this;
    }

    public void setGiverProfile(Profile profile) {
        this.giverProfile = profile;
    }

    public Profile getReceiverProfile() {
        return receiverProfile;
    }

    public Rating receiverProfile(Profile profile) {
        this.receiverProfile = profile;
        return this;
    }

    public void setReceiverProfile(Profile profile) {
        this.receiverProfile = profile;
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
        Rating rating = (Rating) o;
        if (rating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", teamPlayer='" + getTeamPlayer() + "'" +
            ", friendly='" + getFriendly() + "'" +
            ", leader='" + getLeader() + "'" +
            "}";
    }
}
