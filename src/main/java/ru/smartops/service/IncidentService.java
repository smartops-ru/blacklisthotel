package ru.smartops.service;

import ru.smartops.domain.Incident;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Incident}.
 */
public interface IncidentService {

    /**
     * Save a incident.
     *
     * @param incident the entity to save.
     * @return the persisted entity.
     */
    Incident save(Incident incident);

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Incident> findAll(Pageable pageable);


    /**
     * Get the "id" incident.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Incident> findOne(Long id);

    /**
     * Delete the "id" incident.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
