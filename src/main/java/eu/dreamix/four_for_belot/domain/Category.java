package eu.dreamix.four_for_belot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

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

    @Column(name = "games_played")
    private Long gamesPlayed;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> children = new HashSet<>();

    @ManyToOne
    private Category parent;

    @ManyToMany(mappedBy = "categoriesOfInterests")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Profile> interestedProfiles = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> gamesWithThisCategories = new HashSet<>();

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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public Category gamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        return this;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public Category children(Set<Category> categories) {
        this.children = categories;
        return this;
    }

    public Category addChild(Category category) {
        this.children.add(category);
        category.setParent(this);
        return this;
    }

    public Category removeChild(Category category) {
        this.children.remove(category);
        category.setParent(null);
        return this;
    }

    public void setChildren(Set<Category> categories) {
        this.children = categories;
    }

    public Category getParent() {
        return parent;
    }

    public Category parent(Category category) {
        this.parent = category;
        return this;
    }

    public void setParent(Category category) {
        this.parent = category;
    }

    public Set<Profile> getInterestedProfiles() {
        return interestedProfiles;
    }

    public Category interestedProfiles(Set<Profile> profiles) {
        this.interestedProfiles = profiles;
        return this;
    }

    public Category addInterestedProfiles(Profile profile) {
        this.interestedProfiles.add(profile);
        profile.getCategoriesOfInterests().add(this);
        return this;
    }

    public Category removeInterestedProfiles(Profile profile) {
        this.interestedProfiles.remove(profile);
        profile.getCategoriesOfInterests().remove(this);
        return this;
    }

    public void setInterestedProfiles(Set<Profile> profiles) {
        this.interestedProfiles = profiles;
    }

    public Set<Game> getGamesWithThisCategories() {
        return gamesWithThisCategories;
    }

    public Category gamesWithThisCategories(Set<Game> games) {
        this.gamesWithThisCategories = games;
        return this;
    }

    public Category addGamesWithThisCategory(Game game) {
        this.gamesWithThisCategories.add(game);
        game.getCategories().add(this);
        return this;
    }

    public Category removeGamesWithThisCategory(Game game) {
        this.gamesWithThisCategories.remove(game);
        game.getCategories().remove(this);
        return this;
    }

    public void setGamesWithThisCategories(Set<Game> games) {
        this.gamesWithThisCategories = games;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", gamesPlayed=" + getGamesPlayed() +
            "}";
    }
}
