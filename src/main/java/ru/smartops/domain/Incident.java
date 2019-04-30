package ru.smartops.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ru.smartops.domain.enumeration.IncidentType;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private IncidentType type;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "place")
    private String place;

    @Column(name = "hotel")
    private String hotel;

    @Column(name = "person_name_hash")
    private String personNameHash;

    @Column(name = "person_document_hash")
    private String personDocumentHash;

    @Column(name = "phone_number_hash")
    private String phoneNumberHash;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IncidentType getType() {
        return type;
    }

    public Incident type(IncidentType type) {
        this.type = type;
        return this;
    }

    public void setType(IncidentType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public Incident date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public Incident place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHotel() {
        return hotel;
    }

    public Incident hotel(String hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getPersonNameHash() {
        return personNameHash;
    }

    public Incident personNameHash(String personNameHash) {
        this.personNameHash = personNameHash;
        return this;
    }

    public void setPersonNameHash(String personNameHash) {
        this.personNameHash = personNameHash;
    }

    public String getPersonDocumentHash() {
        return personDocumentHash;
    }

    public Incident personDocumentHash(String personDocumentHash) {
        this.personDocumentHash = personDocumentHash;
        return this;
    }

    public void setPersonDocumentHash(String personDocumentHash) {
        this.personDocumentHash = personDocumentHash;
    }

    public String getPhoneNumberHash() {
        return phoneNumberHash;
    }

    public Incident phoneNumberHash(String phoneNumberHash) {
        this.phoneNumberHash = phoneNumberHash;
        return this;
    }

    public void setPhoneNumberHash(String phoneNumberHash) {
        this.phoneNumberHash = phoneNumberHash;
    }

    public String getDescription() {
        return description;
    }

    public Incident description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Incident incident = (Incident) o;
        if (incident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", place='" + getPlace() + "'" +
            ", hotel='" + getHotel() + "'" +
            ", personNameHash='" + getPersonNameHash() + "'" +
            ", personDocumentHash='" + getPersonDocumentHash() + "'" +
            ", phoneNumberHash='" + getPhoneNumberHash() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
