package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.MyUpgradeDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MyUpgradeDomain entity.
 */
public interface MyUpgradeDomainSearchRepository extends ElasticsearchRepository<MyUpgradeDomain, Long> {
}
