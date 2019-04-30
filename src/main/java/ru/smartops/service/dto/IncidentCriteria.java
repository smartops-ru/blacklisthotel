package ru.smartops.service.dto;

import java.io.Serializable;
import java.util.Objects;
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
 * Criteria class for the Incident entity. This class is used in IncidentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /incidents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IncidentCriteria implements Serializable {
    /**
     * Class for filtering IncidentType
     */
    public static class IncidentTypeFilter extends Filter<IncidentType> {
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
