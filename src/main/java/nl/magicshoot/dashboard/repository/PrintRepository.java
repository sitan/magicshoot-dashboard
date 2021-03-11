package nl.magicshoot.dashboard.repository;

import nl.magicshoot.dashboard.domain.Print;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Print entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrintRepository extends JpaRepository<Print, Long> {}
