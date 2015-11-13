package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyUpgradeDomain;
import com.mycompany.myapp.repository.MyUpgradeDomainRepository;
import com.mycompany.myapp.repository.search.MyUpgradeDomainSearchRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MyUpgradeDomain.
 */
@RestController
@RequestMapping("/api")
public class MyUpgradeDomainResource {

    private final Logger log = LoggerFactory.getLogger(MyUpgradeDomainResource.class);

    @Inject
    private MyUpgradeDomainRepository myUpgradeDomainRepository;

    @Inject
    private MyUpgradeDomainSearchRepository myUpgradeDomainSearchRepository;

    /**
     * POST  /myUpgradeDomains -> Create a new myUpgradeDomain.
     */
    @RequestMapping(value = "/myUpgradeDomains",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyUpgradeDomain> createMyUpgradeDomain(@Valid @RequestBody MyUpgradeDomain myUpgradeDomain) throws URISyntaxException {
        log.debug("REST request to save MyUpgradeDomain : {}", myUpgradeDomain);
        if (myUpgradeDomain.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new myUpgradeDomain cannot already have an ID").body(null);
        }
        MyUpgradeDomain result = myUpgradeDomainRepository.save(myUpgradeDomain);
        myUpgradeDomainSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/myUpgradeDomains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myUpgradeDomain", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /myUpgradeDomains -> Updates an existing myUpgradeDomain.
     */
    @RequestMapping(value = "/myUpgradeDomains",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyUpgradeDomain> updateMyUpgradeDomain(@Valid @RequestBody MyUpgradeDomain myUpgradeDomain) throws URISyntaxException {
        log.debug("REST request to update MyUpgradeDomain : {}", myUpgradeDomain);
        if (myUpgradeDomain.getId() == null) {
            return createMyUpgradeDomain(myUpgradeDomain);
        }
        MyUpgradeDomain result = myUpgradeDomainRepository.save(myUpgradeDomain);
        myUpgradeDomainSearchRepository.save(myUpgradeDomain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myUpgradeDomain", myUpgradeDomain.getId().toString()))
            .body(result);
    }

    /**
     * GET  /myUpgradeDomains -> get all the myUpgradeDomains.
     */
    @RequestMapping(value = "/myUpgradeDomains",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MyUpgradeDomain>> getAllMyUpgradeDomains(Pageable pageable)
        throws URISyntaxException {
        Page<MyUpgradeDomain> page = myUpgradeDomainRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/myUpgradeDomains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /myUpgradeDomains/:id -> get the "id" myUpgradeDomain.
     */
    @RequestMapping(value = "/myUpgradeDomains/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyUpgradeDomain> getMyUpgradeDomain(@PathVariable Long id) {
        log.debug("REST request to get MyUpgradeDomain : {}", id);
        return Optional.ofNullable(myUpgradeDomainRepository.findOne(id))
            .map(myUpgradeDomain -> new ResponseEntity<>(
                myUpgradeDomain,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /myUpgradeDomains/:id -> delete the "id" myUpgradeDomain.
     */
    @RequestMapping(value = "/myUpgradeDomains/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMyUpgradeDomain(@PathVariable Long id) {
        log.debug("REST request to delete MyUpgradeDomain : {}", id);
        myUpgradeDomainRepository.delete(id);
        myUpgradeDomainSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myUpgradeDomain", id.toString())).build();
    }

    /**
     * SEARCH  /_search/myUpgradeDomains/:query -> search for the myUpgradeDomain corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/myUpgradeDomains/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MyUpgradeDomain> searchMyUpgradeDomains(@PathVariable String query) {
        return StreamSupport
            .stream(myUpgradeDomainSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
