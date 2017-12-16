package eu.dreamix.four_for_belot.repository;

import eu.dreamix.four_for_belot.domain.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select distinct profile from Profile profile left join fetch profile.categoriesOfInterests")
    List<Profile> findAllWithEagerRelationships();

    @Query("select profile from Profile profile left join fetch profile.categoriesOfInterests where profile.id =:id")
    Profile findOneWithEagerRelationships(@Param("id") Long id);

}
