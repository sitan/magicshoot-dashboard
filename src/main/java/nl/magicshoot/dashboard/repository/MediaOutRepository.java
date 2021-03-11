package nl.magicshoot.dashboard.repository;

import nl.magicshoot.dashboard.domain.MediaOut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MediaOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaOutRepository extends JpaRepository<MediaOut, Long> {}
