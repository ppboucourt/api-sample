package co.tmunited.bluebook.repository;

import co.tmunited.bluebook.domain.ServiceProvider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProvider entity.
 */
@SuppressWarnings("unused")
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {


    Page<ServiceProvider> findAllByDelStatusFalse(Pageable var1);
}
