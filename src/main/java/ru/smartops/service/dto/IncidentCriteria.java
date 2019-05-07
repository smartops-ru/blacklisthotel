package ru.smartops.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ru.smartops.domain.enumeration.IncidentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link ru.smartops.domain.Incident} entity. This class is used
 * in {@link ru.smartops.web.rest.IncidentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /incidents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IncidentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering IncidentType
     */
    public static class IncidentTypeFilter extends Filter<IncidentType> {

        public IncidentTypeFilter() {
        }

        public IncidentTypeFilter(IncidentTypeFilter filter) {
            super(filter);
        }

        @Override
        public IncidentTypeFilter copy() {
            return new IncidentTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IncidentTypeFilter type;

    private LocalDateFilter date;

    private StringFilter place;

    private StringFilter hotel;

    private StringFilter personNameHash;

    private StringFilter personDocumentHash;

    private StringFilter phoneNumberHash;

    private StringFilter description;

    public IncidentCriteria(){
    }

    public IncidentCriteria(IncidentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.place = other.place == null ? null : other.place.copy();
        this.hotel = other.hotel == null ? null : other.hotel.copy();
        this.personNameHash = other.personNameHash == null ? null : other.personNameHash.copy();
        this.personDocumentHash = other.personDocumentHash == null ? null : other.personDocumentHash.copy();
        this.phoneNumberHash = other.phoneNumberHash == null ? null : other.phoneNumberHash.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public IncidentCriteria copy() {
        return new IncidentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IncidentTypeFilter getType() {
        return type;
    }

    public void setType(IncidentTypeFilter type) {
        this.type = type;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public StringFilter getPlace() {
        return place;
    }

    public void setPlace(StringFilter place) {
        this.place = place;
    }

    public StringFilter getHotel() {
        return hotel;
    }

    public void setHotel(StringFilter hotel) {
        this.hotel = hotel;
    }

    public StringFilter getPersonNameHash() {
        return personNameHash;
    }

    public void setPersonNameHash(StringFilter personNameHash) {
        this.personNameHash = personNameHash;
    }

    public StringFilter getPersonDocumentHash() {
        return personDocumentHash;
    }

    public void setPersonDocumentHash(StringFilter personDocumentHash) {
        this.personDocumentHash = personDocumentHash;
    }

    public StringFilter getPhoneNumberHash() {
        return phoneNumberHash;
    }

    public void setPhoneNumberHash(StringFilter phoneNumberHash) {
        this.phoneNumberHash = phoneNumberHash;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IncidentCriteria that = (IncidentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(date, that.date) &&
            Objects.equals(place, that.place) &&
            Objects.equals(hotel, that.hotel) &&
            Objects.equals(personNameHash, that.personNameHash) &&
            Objects.equals(personDocumentHash, that.personDocumentHash) &&
            Objects.equals(phoneNumberHash, that.phoneNumberHash) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        date,
        place,
        hotel,
        personNameHash,
        personDocumentHash,
        phoneNumberHash,
        description
        );
    }

    @Override
    public String toString() {
        return "IncidentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (place != null ? "place=" + place + ", " : "") +
                (hotel != null ? "hotel=" + hotel + ", " : "") +
                (personNameHash != null ? "personNameHash=" + personNameHash + ", " : "") +
                (personDocumentHash != null ? "personDocumentHash=" + personDocumentHash + ", " : "") +
                (phoneNumberHash != null ? "phoneNumberHash=" + phoneNumberHash + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
