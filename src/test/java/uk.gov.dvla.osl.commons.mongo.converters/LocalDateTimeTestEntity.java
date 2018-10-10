package uk.gov.dvla.osl.commons.mongo.converters;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class LocalDateTimeTestEntity {

    @Id
    private UUID id;
    private LocalDateTime  regDate;


    public LocalDateTimeTestEntity() {
    }

    public LocalDateTimeTestEntity(UUID id, LocalDateTime regDate) {
        this.id = id;
        this.regDate =regDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

   public LocalDateTime getRegDate() {
       return regDate;
   }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}

