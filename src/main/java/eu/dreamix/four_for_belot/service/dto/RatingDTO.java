package eu.dreamix.four_for_belot.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import eu.dreamix.four_for_belot.domain.enumeration.RatingScore;
import eu.dreamix.four_for_belot.domain.enumeration.RatingScore;
import eu.dreamix.four_for_belot.domain.enumeration.RatingScore;

/**
 * A DTO for the Rating entity.
 */
public class RatingDTO implements Serializable {

    private Long id;

    private RatingScore teamPlayer;

    private RatingScore friendly;

    private RatingScore leader;

    private Long playedGameId;

    private Long giverProfileId;

    private Long receiverProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingScore getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(RatingScore teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    public RatingScore getFriendly() {
        return friendly;
    }

    public void setFriendly(RatingScore friendly) {
        this.friendly = friendly;
    }

    public RatingScore getLeader() {
        return leader;
    }

    public void setLeader(RatingScore leader) {
        this.leader = leader;
    }

    public Long getPlayedGameId() {
        return playedGameId;
    }

    public void setPlayedGameId(Long gameId) {
        this.playedGameId = gameId;
    }

    public Long getGiverProfileId() {
        return giverProfileId;
    }

    public void setGiverProfileId(Long profileId) {
        this.giverProfileId = profileId;
    }

    public Long getReceiverProfileId() {
        return receiverProfileId;
    }

    public void setReceiverProfileId(Long profileId) {
        this.receiverProfileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if(ratingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", teamPlayer='" + getTeamPlayer() + "'" +
            ", friendly='" + getFriendly() + "'" +
            ", leader='" + getLeader() + "'" +
            "}";
    }
}
