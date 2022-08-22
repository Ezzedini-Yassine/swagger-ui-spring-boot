package com.terrificventures.gearni_backend_spring_boot.repository;

import com.terrificventures.gearni_backend_spring_boot.domain.SupplierUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SupplierUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierUserRepository extends JpaRepository<SupplierUser, Long> {}
