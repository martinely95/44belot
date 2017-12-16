package eu.dreamix.four_for_belot.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Category.class.getName() + ".children", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Location.class.getName() + ".games", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".receivedRatings", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".createdGames", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Notification.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Rating.class.getName(), jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".categoriesOfInterests", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Category.class.getName() + ".interestedProfiles", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Category.class.getName() + ".games", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".givenRatings", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Profile.class.getName() + ".playedGames", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName() + ".ratings", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Game.class.getName() + ".playersProfiles", jcacheConfiguration);
            cm.createCache(eu.dreamix.four_for_belot.domain.Category.class.getName() + ".gamesWithThisCategories", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
