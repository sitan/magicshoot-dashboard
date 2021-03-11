package nl.magicshoot.dashboard.repository;

import nl.magicshoot.dashboard.domain.Reseller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reseller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResellerRepository extends JpaRepository<Reseller, Long> {}
