package nl.magicshoot.dashboard.repository;

import nl.magicshoot.dashboard.domain.MediaIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MediaIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaInRepository extends JpaRepository<MediaIn, Long> {}
