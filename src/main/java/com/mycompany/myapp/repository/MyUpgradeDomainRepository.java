package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyUpgradeDomain;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyUpgradeDomain entity.
 */
public interface MyUpgradeDomainRepository extends JpaRepository<MyUpgradeDomain,Long> {

}
