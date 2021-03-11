package nl.magicshoot.dashboard.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, nl.magicshoot.dashboard.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, nl.magicshoot.dashboard.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, nl.magicshoot.dashboard.domain.User.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Authority.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.User.class.getName() + ".authorities");
            createCache(cm, nl.magicshoot.dashboard.domain.Admin.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Admin.class.getName() + ".resellers");
            createCache(cm, nl.magicshoot.dashboard.domain.Reseller.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Reseller.class.getName() + ".companies");
            createCache(cm, nl.magicshoot.dashboard.domain.Company.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Company.class.getName() + ".events");
            createCache(cm, nl.magicshoot.dashboard.domain.Company.class.getName() + ".customers");
            createCache(cm, nl.magicshoot.dashboard.domain.Company.class.getName() + ".contacts");
            createCache(cm, nl.magicshoot.dashboard.domain.Contact.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Contact.class.getName() + ".quotes");
            createCache(cm, nl.magicshoot.dashboard.domain.Customer.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Quote.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Quote.class.getName() + ".invoices");
            createCache(cm, nl.magicshoot.dashboard.domain.Invoice.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Event.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Device.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.MediaIn.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.MediaOut.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Label.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Print.class.getName());
            createCache(cm, nl.magicshoot.dashboard.domain.Payments.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
