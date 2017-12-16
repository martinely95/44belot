package eu.dreamix.four_for_belot.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import eu.dreamix.four_for_belot.domain.enumeration.Language;
import eu.dreamix.four_for_belot.domain.enumeration.Sex;

/**
 * A DTO for the Profile entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    private Language language;

    private Sex sex;

    private Integer age;

    private Long gamesPlayed;

    private Long gamesWon;

    private Long gamesLost;

    private Long userId;

    private Set<CategoryDTO> categoriesOfInterests = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Long getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Long getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Long gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CategoryDTO> getCategoriesOfInterests() {
        return categoriesOfInterests;
    }

    public void setCategoriesOfInterests(Set<CategoryDTO> categories) {
        this.categoriesOfInterests = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if(profileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
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
