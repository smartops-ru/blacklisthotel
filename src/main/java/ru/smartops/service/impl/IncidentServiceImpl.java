package ru.smartops.service.impl;

import ru.smartops.service.IncidentService;
import ru.smartops.domain.Incident;
import ru.smartops.repository.IncidentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentServiceImpl.class);

    private final IncidentRepository incidentRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Save a incident.
     *
     * @param incident the entity to save
     * @return the persisted entity
     */
    @Override
    public Incident save(Incident incident) {
        log.debug("Request to save Incident : {}", incident);
        return incidentRepository.save(incident);
    }

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Incident> findAll(Pageable pageable) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll(pageable);
    }


    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Incident> findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findById(id);
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
    }
}
