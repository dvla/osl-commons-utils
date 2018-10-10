package uk.gov.dvla.osl.commons.mongo.converters;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class LocalDateTestEntity {

    @Id
    private UUID id;
    private LocalDate  regDate;


    public LocalDateTestEntity() {
    }

    public LocalDateTestEntity(UUID id, LocalDate regDate) {
        this.id = id;
        this.regDate = regDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }
}

